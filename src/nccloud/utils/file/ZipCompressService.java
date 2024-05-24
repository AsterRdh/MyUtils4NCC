package nccloud.utils.file;


import nccloud.utils.file.beans.ProgressBean;
import nccloud.utils.file.beans.ProgressRateBean;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 压缩ZIP
 */
public class ZipCompressService {
    /**
     *
     * 将文件夹及文件夹包含的内容压缩成zip文件
     * (为了解决中文乱码的问题，ZipOutputStream用org.apache.tools.zip.*)
     *
     * @param inputFile 源文件
     * @param delFlag 删除源文件标记
     * @return File 压缩后的文件
     */
    public File zipCompress(File inputFile, boolean delFlag,String key, Consumer<ProgressBean> callUpdateProgress) throws Exception{
        File zipFile = null;
        //创建zip输出流
        //为了解决中文乱码的问题,ZipOutputStream用org.apache.tools.zip.*
        //不要用 java.util.zip.*
        ZipOutputStream zos = null;
        if(inputFile != null && inputFile.exists()) {
            try {
                //为了获取进度所以先获得所有文件记数

                ProgressRateBean docPathSetProgress = new ProgressRateBean(count(inputFile),85,95);
                String path = inputFile.getCanonicalPath();
                String zipFileName = path + ".zip";
                zipFile = new File(zipFileName);
                if(zipFile.exists()) {
                    zipFile.delete();
                }
                zipFile.createNewFile();//创建文件
                zos = new ZipOutputStream(new FileOutputStream(zipFile));
                //解决中文乱码问题,指定编码GBK
                zos.setEncoding("GBK");
                //压缩文件或文件夹
                this.compressFile(zos, inputFile, inputFile.getName(),key,docPathSetProgress,callUpdateProgress);
            }catch(Exception e) {
                callUpdateProgress.accept(new ProgressBean("文件压缩异常",key,0.99f));
                System.out.println("文件压缩异常：" + e);
                throw e;
            }finally {
                try {
                    if(zos != null) {
                        //先调用outputStream的flush()再关闭流；
                        //流如果未正常关闭,则会在解压的时候出现压缩文件损坏的现象
                        zos.flush();
                        zos.close();
                    }

                    if(delFlag) {
                        //递归删除源文件及源文件夹
                        this.deleteFile(inputFile);
                    }
                }catch(Exception ex) {
                    System.out.println("输出流关闭异常：" + ex);
                }
            }
        }
        return zipFile;
    }
    public File zipCompress(File inputFile, boolean delFlag) throws Exception{
        return zipCompress(inputFile,delFlag,false);
    }
    public File zipCompress(File inputFile, boolean delFlag,boolean ignoreTop) throws Exception{
        File zipFile = null;
        //创建zip输出流
        //为了解决中文乱码的问题,ZipOutputStream用org.apache.tools.zip.*
        //不要用 java.util.zip.*
        ZipOutputStream zos = null;
        if(inputFile != null && inputFile.exists()) {
            try {
                //为了获取进度所以先获得所有文件记数
                String path = inputFile.getCanonicalPath();
                String zipFileName = path + ".zip";
                zipFile = new File(zipFileName);
                if(zipFile.exists()) {
                    zipFile.delete();
                }
                zipFile.createNewFile();//创建文件
                zos = new ZipOutputStream(new FileOutputStream(zipFile));
                //解决中文乱码问题,指定编码GBK
                zos.setEncoding("GBK");
                //压缩文件或文件夹
                if (ignoreTop){
                    for (File file : inputFile.listFiles()) {
                        this.compressFile(zos, file, file.getName());
                    }
                }else {
                    this.compressFile(zos, inputFile, inputFile.getName());
                }



            }catch(Exception e) {
                System.out.println("文件压缩异常：" + e);
                throw e;
            }finally {
                try {
                    if(zos != null) {
                        //先调用outputStream的flush()再关闭流；
                        //流如果未正常关闭,则会在解压的时候出现压缩文件损坏的现象
                        zos.flush();
                        zos.close();
                    }

                    if(delFlag) {
                        //递归删除源文件及源文件夹
                        this.deleteFile(inputFile);
                    }
                }catch(Exception ex) {
                    System.out.println("输出流关闭异常：" + ex);
                }
            }
        }
        return zipFile;
    }
    /**
     * 压缩文件或文件夹
     * (ZipEntry 使用org.apache.tools.zip.*，不要用 java.util.zip.*)
     *
     * @param zos zip输出流
     * @param sourceFile 源文件
     * @param baseName 父路径
     * @param callBack
     * @throws Exception 异常
     */
    private void compressFile(ZipOutputStream zos, File sourceFile, String baseName,String key, ProgressRateBean docPathSetProgress, Consumer<ProgressBean> callBack) throws Exception{
        if(!sourceFile.exists()) {
            return;
        }
        docPathSetProgress.addSept();
        //若路径为目录（文件夹）
        if(sourceFile.isDirectory()) {
            //取出文件夹中的文件（或子文件夹）
            File[] fileList = sourceFile.listFiles();
            //若文件夹为空，则创建一个目录进入点
            if(fileList.length == 0) {
                //文件名称后跟File.separator表示这是一个文件夹
                zos.putNextEntry(new ZipEntry(baseName + File.separator));
                //若文件夹非空，则递归调用compressFile,对文件夹中的每个文件或每个文件夹进行压缩
            }else {
                for(int i = 0; i < fileList.length; i++) {
                    this.compressFile(zos, fileList[i],
                            baseName + File.separator + fileList[i].getName(),key,docPathSetProgress, callBack);
                }
            }

            //若为文件,则先创建目录进入点,再将文件写入zip文件中
        }else {
            ZipEntry ze = new ZipEntry(baseName);
            //设置ZipEntry的最后修改时间为源文件的最后修改时间
            ze.setTime(sourceFile.lastModified());
            zos.putNextEntry(ze);

            FileInputStream fis = new FileInputStream(sourceFile);
            this.copyStream(fis, zos);
            try {
                if(fis != null) {
                    fis.close();
                }
            }catch(Exception e) {
                System.out.println("输入流关闭异常：" + e);
            }
        }
        callBack.accept(new ProgressBean("压缩中 ...",key,docPathSetProgress.getNowRate()));
    }

    private void compressFile(ZipOutputStream zos, File sourceFile, String baseName) throws Exception{
        if(!sourceFile.exists()) {
            return;
        }
        //若路径为目录（文件夹）
        if(sourceFile.isDirectory()) {
            //取出文件夹中的文件（或子文件夹）
            File[] fileList = sourceFile.listFiles();
            //若文件夹为空，则创建一个目录进入点
            if(fileList.length == 0) {
                //文件名称后跟File.separator表示这是一个文件夹
                zos.putNextEntry(new ZipEntry(baseName + File.separator));
                //若文件夹非空，则递归调用compressFile,对文件夹中的每个文件或每个文件夹进行压缩
            }else {
                for(int i = 0; i < fileList.length; i++) {
                    this.compressFile(zos, fileList[i],
                            baseName + File.separator + fileList[i].getName());
                }
            }

            //若为文件,则先创建目录进入点,再将文件写入zip文件中
        }else {
            ZipEntry ze = new ZipEntry(baseName);
            //设置ZipEntry的最后修改时间为源文件的最后修改时间
            ze.setTime(sourceFile.lastModified());
            zos.putNextEntry(ze);

            FileInputStream fis = new FileInputStream(sourceFile);
            this.copyStream(fis, zos);
            try {
                if(fis != null) {
                    fis.close();
                }
            }catch(Exception e) {
                System.out.println("输入流关闭异常：" + e);
            }
        }
    }

    /**
     * 流拷贝
     *
     * @param in 输入流
     * @param out 输出流
     * @throws IOException
     */
    private void copyStream(InputStream in, OutputStream out) throws IOException{
        int bufferLength = 1024 * 100;
        synchronized(in) {
            synchronized(out) {
                int count = 0;
                byte[] buffer = new byte[bufferLength];
                while((count = in.read(buffer, 0, bufferLength)) != -1) {
                    out.write(buffer, 0, count);
                }
                out.flush();
            }
        }
    }

    /**
     * 递归删除文件夹中的目录及文件
     *
     * @param sourceFile
     * @throws Exception
     */
    public void deleteFile(File sourceFile) throws Exception{
        //如果路径为目录
        if(sourceFile.isDirectory()) {
            //取出文件夹中的文件或子文件夹
            File[] fList = sourceFile.listFiles();
            if(fList.length == 0) {
                sourceFile.delete();
            }else {
                for(int i = 0; i < fList.length; i++) {
                    this.deleteFile(fList[i]);
                }
                sourceFile.delete();
            }
            //如果为文件则直接删除
        }else {
            sourceFile.delete();
        }
    }

    /**
     *     当前路径下若已有同名文件又不愿意覆盖，
     *     则依次追加后缀
     *
     * @param path
     * @param docName
     * @return
     * @throws Exception
     */
    private File createFile(String path, final String docName) throws Exception{
        //创建目标文件
        File destFile = new File(path, docName);
        //如果路径下存在同名文件又不愿意覆盖，
        //那么则依次给文件加后缀（2）、（3）……
        if(destFile.exists()) {
            int i = 1;
            do {
                ++i;
                //按“.”分割
                String[] doc = docName.split("\\.");
                destFile = new File(path, doc[0] + "(" + i + ")" + "." + doc[1]);

                //直到文件创建成功则跳出循环
            }while(!destFile.createNewFile());
        }else {
            destFile.createNewFile();
        }
        return destFile;
    }

    /**
     * 过滤非法字符
     * @param inputStr 输入字符串
     * @return
     */
    private String filterIllegalSymbol(String inputStr) {
        if(StringUtils.isEmpty(inputStr)) {
            return null;
        }
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(inputStr);
        //若包含非法字符则循环处理
        if(matcher.find()) {
            char[] regExCharArray = regEx.toCharArray();
            for(int i = 0; i < regExCharArray.length; i++) {
                char c = regExCharArray[i];
                System.out.println(i + " 当前字符：" + c);
                if(inputStr.indexOf(c) != -1) {
                    System.out.println("过滤前：" + inputStr);
                    //因为某些符号在正则表达示中有相应意义，所以要加上“\\”表示转义，
                    //否则会报错，例如java.util.regex.PatternSyntaxException: Dangling meta character '*' near index 0
                    inputStr = inputStr.replaceAll("\\\\" + String.valueOf(c), "");
                    System.out.println("过滤后：" +inputStr);
                }
            }
        }
        //测试用
        System.out.println(inputStr);
        return inputStr;

    }

    public int count(File file){
        System.out.println(file.getPath());
        if (file.isDirectory()){
            File[] files = file.listFiles();
            int i=1;
            for (File file1 : files) {
                i+=count(file1);
            }
            return i;
        }else {

            return 1;
        }
    }

    public static void main(String[] args) throws Exception {
        ZipCompressService service = new ZipCompressService();
        File file=new File("C:\\Users\\Aster\\Downloads\\云南滇中东盟国际物流园建设项目设计施工一体化2023-06-11 (9)\\337173e5-c604-42f4-9de5-2c760048dc36");
        service.zipCompress(file,false,true);
        //service.filterIllegalSymbol("这是一个*包含非/法字:符$%<的字符串>|");
    }
}
