package com.triton.johnson_tap_app.requestpojo;

public class GetFieldListRequest {

//    private String group_id;
//    private String sub_group_id;
//    private String job_id;
//    private String user_id ;
//    private String user_role;
//
//
//
//    public String getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(String user_id) {
//        this.user_id = user_id;
//    }
//
//    public String getJob_id() {
//        return job_id;
//    }
//
//    public void setJob_id(String job_id) {
//        this.job_id = job_id;
//    }
//
//    public String getGroup_id() {
//        return group_id;
//    }
//
//    public void setGroup_id(String group_id) {
//        this.group_id = group_id;
//    }
//
//    public String getSub_group_id() {
//        return sub_group_id;
//    }
//
//    public void setSub_group_id(String sub_group_id) {
//        this.sub_group_id = sub_group_id;
//    }
//
//    public String getUser_role() {
//        return user_role;
//    }
//
//    public void setUser_role(String user_role) {
//        this.user_role = user_role;
//    }

    private String job_status_type;
    private String job_id;
    private String job_date;
    private String service_type;

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getJob_status_type() {
        return job_status_type;
    }

    public void setJob_status_type(String job_status_type) {
        this.job_status_type = job_status_type;
    }

    public String getJob_date() {
        return job_date;
    }

    public void setJob_date(String job_date) {
        this.job_date = job_date;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }
}
