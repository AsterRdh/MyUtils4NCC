package nccloud.utils.file.beans;

import nc.vo.pub.filesystem.NCFileVO;

public class DownloadFileBean {
    private NCFileVO fileVO;
    private String path;

    public DownloadFileBean(NCFileVO fileVO, String path) {
    this.fileVO = fileVO;
        this.path = path;
    }

    public NCFileVO getFileVO() {
        return fileVO;
    }

    public void setFileVO(NCFileVO fileVO) {
        this.fileVO = fileVO;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
