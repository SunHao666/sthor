package com.hao.jsthor.bean;

import java.util.List;

public class FileBean {


    /**
     * resUrl : https://www.361web.net/jst/res/files/
     * code : 200
     * data : {"totalRow":1,"pageNumber":1,"firstPage":true,"lastPage":true,"totalPage":1,"pageSize":1,"list":[{"sort_inx":1,"file_path":"157512545468234268.mp4","create_time":"2019-11-30 22:50:41","id":1,"title":"积水潭之歌"}]}
     * success : true
     * mess : 成功
     */

    private String resUrl;
    private int code;
    private DataBean data;
    private boolean success;
    private String mess;

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public static class DataBean {
        /**
         * totalRow : 1
         * pageNumber : 1
         * firstPage : true
         * lastPage : true
         * totalPage : 1
         * pageSize : 1
         * list : [{"sort_inx":1,"file_path":"157512545468234268.mp4","create_time":"2019-11-30 22:50:41","id":1,"title":"积水潭之歌"}]
         */

        private int totalRow;
        private int pageNumber;
        private boolean firstPage;
        private boolean lastPage;
        private int totalPage;
        private int pageSize;
        private List<ListBean> list;

        public int getTotalRow() {
            return totalRow;
        }

        public void setTotalRow(int totalRow) {
            this.totalRow = totalRow;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public boolean isFirstPage() {
            return firstPage;
        }

        public void setFirstPage(boolean firstPage) {
            this.firstPage = firstPage;
        }

        public boolean isLastPage() {
            return lastPage;
        }

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * sort_inx : 1
             * file_path : 157512545468234268.mp4
             * create_time : 2019-11-30 22:50:41
             * id : 1
             * title : 积水潭之歌
             */

            private int sort_inx;
            private String file_path;
            private String create_time;
            private int id;
            private String title;

            public int getSort_inx() {
                return sort_inx;
            }

            public void setSort_inx(int sort_inx) {
                this.sort_inx = sort_inx;
            }

            public String getFile_path() {
                return file_path;
            }

            public void setFile_path(String file_path) {
                this.file_path = file_path;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
