package com.hao.jsthor.bean;

import java.util.List;

public class WebContentBean {


    /**
     * code : 200
     * data : {"totalRow":1,"pageNumber":1,"firstPage":true,"lastPage":true,"totalPage":1,"pageSize":1,"list":[{"sort_inx":1,"create_time":"2019-11-30 22:24:39","link_url":"http://www.jst-hosp.com.cn/","id":1,"title":"积水潭医院官网"}]}
     * success : true
     * mess : 成功
     */

    private int code;
    private DataBean data;
    private boolean success;
    private String mess;

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
         * list : [{"sort_inx":1,"create_time":"2019-11-30 22:24:39","link_url":"http://www.jst-hosp.com.cn/","id":1,"title":"积水潭医院官网"}]
         */

        private int totalRow;
        private int pageNumber;
        private boolean firstPage;
        private boolean lastPage;
        private int totalPage;
        private int pageSize;
        private List<WebContentListBean> list;

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

        public List<WebContentListBean> getList() {
            return list;
        }

        public void setList(List<WebContentListBean> list) {
            this.list = list;
        }

        public static class WebContentListBean {
            /**
             * sort_inx : 1
             * create_time : 2019-11-30 22:24:39
             * link_url : http://www.jst-hosp.com.cn/
             * id : 1
             * title : 积水潭医院官网
             */

            private int sort_inx;
            private String create_time;
            private String link_url;
            private int id;
            private String title;

            public int getSort_inx() {
                return sort_inx;
            }

            public void setSort_inx(int sort_inx) {
                this.sort_inx = sort_inx;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getLink_url() {
                return link_url;
            }

            public void setLink_url(String link_url) {
                this.link_url = link_url;
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
