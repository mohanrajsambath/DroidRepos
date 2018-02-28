package org.next.equmed.bal;

import java.util.ArrayList;

/**
 * Created by nextmoveo-1 on 10/12/15.
 */
public class Bean {

    // common

    String flag;
    String isactive;
    String sync_status;
    String created_by;
    String created_on;
    String modified_by;
    String modified_on;
    // User

    String user_id;
    String user_email;

    String user_first_name;
    String user_last_name;
    String user_phoneno;
    String user_password;
    String user_image;
    String user_admin;
    String user_hospital;
    String user_effect_startdate;
    String user_effect_enddate;


    //Equipment Enrollment

    String eq_location_code;
    String eq_serialno;
    String eq_make;
    String eq_model;
    String eq_id;
    String eq_install_date;
    String eq_service_tagno;
    String eq_notes;

    String eq_enroll_id;
    private String trn_img_encrypted_name;
    private String trn_img_path;

    public String geteq_enroll_id() {
        return eq_enroll_id;
    }

    public void seteq_enroll_id(String eq_enroll_id) {
        this.eq_enroll_id = eq_enroll_id;
    }


    //Mst Equipment  Enrollment

    String hos_name;
    String hos_Id;
    String hos_location;
    String hospital_desc;
    String hospital_address1;
    String hospital_address2;
    String hospital_address3;
    String hospital_state;
    String hospital_country;
    String hospital_phno1;
    String hospital_phno2;
    String hospital_email;
    String hospital_notes;
    String standard_equipments;
    boolean assignedHospital;

    public String getNew_Img_upload() {
        return new_Img_upload;
    }

    public void setNew_Img_upload(String new_Img_upload) {
        this.new_Img_upload = new_Img_upload;
    }

    String new_Img_upload;


    public boolean isAssignedHospital() {
        return assignedHospital;
    }

    public void setAssignedHospital(boolean assignedHospital) {
        this.assignedHospital = assignedHospital;
    }


    public String getHospital_desc() {
        return hospital_desc;
    }

    public void setHospital_desc(String hospital_desc) {
        this.hospital_desc = hospital_desc;
    }
    public void setstandard_equipments(String standard_equipments) {
        this.standard_equipments = standard_equipments;
    }

    public String getHospital_address1() {
        return hospital_address1;
    }

    public void setHospital_address1(String hospital_address1) {
        this.hospital_address1 = hospital_address1;
    }

    public String getHospital_address2() {
        return hospital_address2;
    }

    public void setHospital_address2(String hospital_address2) {
        this.hospital_address2 = hospital_address2;
    }

    public String getHospital_address3() {
        return hospital_address3;
    }

    public void setHospital_address3(String hospital_address3) {
        this.hospital_address3 = hospital_address3;
    }

    public String getHospital_state() {
        return hospital_state;
    }

    public void setHospital_state(String hospital_state) {
        this.hospital_state = hospital_state;
    }

    public String getHospital_country() {
        return hospital_country;
    }

    public void setHospital_country(String hospital_country) {
        this.hospital_country = hospital_country;
    }

    public String getHospital_phno1() {
        return hospital_phno1;
    }

    public void setHospital_phno1(String hospital_phno1) {
        this.hospital_phno1 = hospital_phno1;
    }

    public String getHospital_phno2() {
        return hospital_phno2;
    }

    public void setHospital_phno2(String hospital_phno2) {
        this.hospital_phno2 = hospital_phno2;
    }

    public String getHospital_email() {
        return hospital_email;
    }

    public String getStandard_Equipments() {
        return standard_equipments;
    }

    public void setHospital_email(String hospital_email) {
        this.hospital_email = hospital_email;
    }

    public String getHospital_notes() {
        return hospital_notes;
    }

    public void setHospital_notes(String hospital_notes) {
        this.hospital_notes = hospital_notes;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getHos_name() {
        return hos_name;
    }

    public void setHos_name(String hos_name) {
        this.hos_name = hos_name;
    }

    public String getHos_Id() {
        return hos_Id;
    }

    public void setHos_Id(String hos_Id) {
        this.hos_Id = hos_Id;
    }

    public String getHos_location() {
        return hos_location;
    }

    public void setHos_location(String hos_location) {
        this.hos_location = hos_location;
    }


    public String getEq_name() {
        return eq_name;
    }

    public void setEq_name(String eq_name) {
        this.eq_name = eq_name;
    }

    String eq_name;

    public String getEq_location_code() {
        return eq_location_code;
    }

    public void setEq_location_code(String eq_location_code) {
        this.eq_location_code = eq_location_code;
    }

    public String getEq_serialno() {
        return eq_serialno;
    }

    public void setEq_serialno(String eq_serialno) {
        this.eq_serialno = eq_serialno;
    }

    String eq_gps;
    public String getEq_gps_coordinates() {
        return eq_gps;
    }

    public void setEq_gps_coordinates(String eq_gps) {
        this.eq_gps = eq_gps;
    }

    public String getEq_make() {
        return eq_make;
    }

    public void setEq_make(String eq_make) {
        this.eq_make = eq_make;
    }

    public String getEq_model() {
        return eq_model;
    }

    public void setEq_model(String eq_model) {
        this.eq_model = eq_model;
    }

    public String getEq_id() {
        return eq_id;
    }

    public void setEq_id(String eq_id) {
        this.eq_id = eq_id;
    }

    public String getEq_install_date() {
        return eq_install_date;
    }

    public void setEq_install_date(String eq_install_date) {
        this.eq_install_date = eq_install_date;
    }

    public String getEq_service_tagno() {
        return eq_service_tagno;
    }

    public void setEq_service_tagno(String eq_service_tagno) {
        this.eq_service_tagno = eq_service_tagno;
    }

    public String getEq_notes() {
        return eq_notes;
    }

    public void setEq_notes(String eq_notes) {
        this.eq_notes = eq_notes;
    }

    public String getEq_install_status() {
        return eq_install_status;
    }

    public void setEq_install_status(String eq_install_status) {
        this.eq_install_status = eq_install_status;
    }

    public String getEq_install_notes() {
        return eq_install_notes;
    }

    public void setEq_install_notes(String eq_install_notes) {
        this.eq_install_notes = eq_install_notes;
    }

    public String getEq_working_status() {
        return eq_working_status;
    }

    public void setEq_working_status(String eq_working_status) {
        this.eq_working_status = eq_working_status;
    }

    public String getEq_working_notes() {
        return eq_working_notes;
    }

    public void setEq_working_notes(String eq_working_notes) {
        this.eq_working_notes = eq_working_notes;
    }

    public String getImg_encrypted_data() {
        return img_encrypted_data;
    }

    public void setImg_encrypted_data(String img_encrypted_data) {
        this.img_encrypted_data = img_encrypted_data;
    }

    public String getEq_emroll_ups() {
        return eq_emroll_ups;
    }

    public void setEq_emroll_ups(String eq_emroll_ups) {
        this.eq_emroll_ups = eq_emroll_ups;
    }

    public String getEq_emroll_manual() {
        return eq_emroll_manual;
    }

    public void setEq_emroll_manual(String eq_emroll_manual) {
        this.eq_emroll_manual = eq_emroll_manual;
    }

    public String getEq_emroll_stabilizer() {
        return eq_emroll_stabilizer;
    }

    public void setEq_emroll_stabilizer(String eq_emroll_stabilizer) {
        this.eq_emroll_stabilizer = eq_emroll_stabilizer;
    }

    public String getEq_emroll_others() {
        return eq_emroll_others;
    }

    public void setEq_emroll_others(String eq_emroll_others) {
        this.eq_emroll_others = eq_emroll_others;
    }

    String eq_install_status;
    String eq_install_notes;
    String eq_working_status;
    String eq_working_notes;


    //Image
    String img_encrypted_data;


    //Trn_Equipment_Enroll_Acessories

    String eq_emroll_ups;
    String eq_emroll_manual;
    String eq_emroll_stabilizer;
    String eq_emroll_others;
    String isstandard;


    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getIsactive() {
        return isactive;
    }

    public String getIsStandard() {
        return isstandard;
    }


    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public void setIsStandard(String isstandard) {
        this.isstandard = isstandard;
    }

    public String getSync_status() {
        return sync_status;
    }

    public void setSync_status(String sync_status) {
        this.sync_status = sync_status;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getModified_on() {
        return modified_on;
    }

    public void setModified_on(String modified_on) {
        this.modified_on = modified_on;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_email(int position) {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_first_name() {
        return user_first_name;
    }

    public void setUser_first_name(String user_first_name) {
        this.user_first_name = user_first_name;
    }

    public String getUser_last_name() {
        return user_last_name;
    }

    public void setUser_last_name(String user_last_name) {
        this.user_last_name = user_last_name;
    }

    public String getUser_phoneno() {
        return user_phoneno;
    }

    public void setUser_phoneno(String user_phoneno) {
        this.user_phoneno = user_phoneno;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_admin() {
        return user_admin;
    }

    public void setUser_admin(String user_admin) {
        this.user_admin = user_admin;
    }

    public String getUser_hospital() {
        return user_hospital;
    }

    public void setUser_hospital(String user_hospital) {
        this.user_hospital = user_hospital;
    }

    public String getUser_effect_startdate() {
        return user_effect_startdate;
    }

    public void setUser_effect_startdate(String user_effect_startdate) {
        this.user_effect_startdate = user_effect_startdate;
    }

    public String getUser_effect_enddate() {
        return user_effect_enddate;
    }

    public void setUser_effect_enddate(String user_effect_enddate) {
        this.user_effect_enddate = user_effect_enddate;
    }


    //    Trn_Document
    //    Trn_Document
    String trn_doc_doc_id;
    String trn_doc_id;
    String trn_doc_name;
    String trn_doc_encrypted_data;

    public String getTrn_doc_filepath_filname() {
        return trn_doc_filepath_filname;
    }

    public void setTrn_doc_filepath_filname(String trn_doc_filepath_filname) {
        this.trn_doc_filepath_filname = trn_doc_filepath_filname;
    }

    String trn_doc_filepath_filname;
    String trn_doc_type;
    String trn_flag;
    String trn_sync_status;
    String trn_created_by;
    String trn_created_on;
    String trn_modified_by;
    String trn_modified_on;


    public ArrayList<Bean> getTrnDocumentArray() {
        return trnDocumentArray;
    }

    public void setTrnDocumentArray(ArrayList<Bean> trnDocumentArray) {
        this.trnDocumentArray = trnDocumentArray;
    }

    ArrayList<Bean> trnDocumentArray;

    public String getEq_enroll_id() {
        return eq_enroll_id;
    }

    public void setEq_enroll_id(String eq_enroll_id) {
        this.eq_enroll_id = eq_enroll_id;
    }

    public String getTrn_doc_doc_id() {
        return trn_doc_doc_id;
    }

    public void setTrn_doc_doc_id(String trn_doc_doc_id) {
        this.trn_doc_doc_id = trn_doc_doc_id;
    }

    public String getTrn_doc_id() {
        return trn_doc_id;
    }

    public void setTrn_doc_id(String trn_doc_id) {
        this.trn_doc_id = trn_doc_id;
    }

    public String getTrn_doc_name() {
        return trn_doc_name;
    }

    public void setTrn_doc_name(String trn_doc_name) {
        this.trn_doc_name = trn_doc_name;
    }

    public String getTrn_doc_encrypted_data() {
        return trn_doc_encrypted_data;
    }

    public void setTrn_doc_encrypted_data(String trn_doc_encrypted_data) {
        this.trn_doc_encrypted_data = trn_doc_encrypted_data;
    }

    public String getTrn_doc_type() {
        return trn_doc_type;
    }

    public void setTrn_doc_type(String trn_doc_type) {
        this.trn_doc_type = trn_doc_type;
    }

    public String getTrn_flag() {
        return trn_flag;
    }

    public void setTrn_flag(String trn_flag) {
        this.trn_flag = trn_flag;
    }

    public String getTrn_sync_status() {
        return trn_sync_status;
    }

    public void setTrn_sync_status(String trn_sync_status) {
        this.trn_sync_status = trn_sync_status;
    }

    public String getTrn_created_by() {
        return trn_created_by;
    }

    public void setTrn_created_by(String trn_created_by) {
        this.trn_created_by = trn_created_by;
    }

    public String getTrn_created_on() {
        return trn_created_on;
    }

    public void setTrn_created_on(String trn_created_on) {
        this.trn_created_on = trn_created_on;
    }

    public String getTrn_modified_by() {
        return trn_modified_by;
    }

    public void setTrn_modified_by(String trn_modified_by) {
        this.trn_modified_by = trn_modified_by;
    }

    public String getTrn_modified_on() {
        return trn_modified_on;
    }

    public void setTrn_modified_on(String trn_modified_on) {
        this.trn_modified_on = trn_modified_on;
    }


    String trn_img_img_id;
    String trn_img_id;

    public String getTrn_img_img_id() {
        return trn_img_img_id;
    }

    public void setTrn_img_img_id(String trn_img_img_id) {
        this.trn_img_img_id = trn_img_img_id;
    }

    public String getTrn_img_id() {
        return trn_img_id;
    }

    public void setTrn_img_id(String trn_img_id) {
        this.trn_img_id = trn_img_id;
    }

    public String getTrn_img_name() {
        return trn_img_name;
    }

    public void setTrn_img_name(String trn_img_name) {
        this.trn_img_name = trn_img_name;
    }

    public String getTrn_img_encrypted_data() {
        return trn_img_encrypted_data;
    }

    public String getTrn_img_encrypted_name() {
        return trn_img_encrypted_name;
    }

    public String getTrn_img_path() {
        return trn_img_path;
    }

    public void setTrn_img_encrypted_data(String trn_img_encrypted_data) {
        this.trn_img_encrypted_data = trn_img_encrypted_data;
    }

    String trn_img_name;
    String trn_img_encrypted_data;


    public void setTrn_img_encrypted_name(String trn_img_encrypted_name) {
        this.trn_img_encrypted_name = trn_img_encrypted_name;
    }

    public void setTrn_img_path(String trn_img_path) {
        this.trn_img_path = trn_img_path;
    }


    /**
     * Murali 10-March-2016
     **/

    String service_id;
    String service_date_time;
    String service_duration;
    String service_type;
    String service_notes;
    String service_approved_by;
    String serviced_by;
    String service_invoice;

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public void setService_datetime(String service_date_time) {
        this.service_date_time = service_date_time;
    }

    public void setService_duration(String service_duration) {
        this.service_duration = service_duration;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public void setService_notes(String service_notes) {
        this.service_notes = service_notes;
    }

    public void setService_approved_by(String service_approved_by) {
        this.service_approved_by = service_approved_by;
    }

    public void setServiced_by(String serviced_by) {
        this.serviced_by = serviced_by;
    }

    public void setService_invoice(String service_invoice) {
        this.service_invoice = service_invoice;
    }

    public String getService_id() {
        return service_id;
    }

    public String getService_datetime() {
        return service_date_time;
    }

    public String getService_duration() {
        return service_duration;
    }

    public String getService_type() {
        return service_type;
    }

    public String getService_notes() {
        return service_notes;
    }

    public String getService_approved_by() {
        return service_approved_by;
    }

    public String getServiced_by() {
        return serviced_by;
    }

    public String getService_invoice() {
        return service_invoice;
    }

    String consumables_id;
    String consumables_name;
    String consumables_description;
    String consumables_type_of_usage;
    String consumables_usage_parameter;
    String consumables_quantity;
    String consumables_notes;
    String consumables_uom;
    String consumables_current_stock;

    public void setConsumables_id(String consumables_id) {
        this.consumables_id = consumables_id;
    }

    public void setConsumables_name(String consumables_name) {
        this.consumables_name = consumables_name;
    }

    public void setConsumables_description(String consumables_description) {
        this.consumables_description = consumables_description;
    }

    public void setConsumables_type_of_usage(String consumables_type_of_usage) {
        this.consumables_type_of_usage = consumables_type_of_usage;
    }

    public void setConsumables_usage_parameter(String consumables_usage_parameter) {
        this.consumables_usage_parameter = consumables_usage_parameter;
    }

    public void setConsumables_quantity(String consumables_quantity) {
        this.consumables_quantity = consumables_quantity;
    }

    public void setConsumables_notes(String consumables_notes) {
        this.consumables_notes = consumables_notes;
    }

    public void setConsumables_uom(String consumables_uom) {
        this.consumables_uom = consumables_uom;
    }

    public void setConsumables_current_stock(String consumables_current_stock) {
        this.consumables_current_stock = consumables_current_stock;
    }

    public String getConsumables_id() {
        return consumables_id;
    }

    public String getConsumables_name() {
        return consumables_name;
    }

    public String getConsumables_description() {
        return consumables_description;
    }

    public String getConsumables_type_of_usage() {
        return consumables_type_of_usage;
    }

    public String getConsumables_usage_parameter() {
        return consumables_usage_parameter;
    }

    public String getConsumables_quantity() {
        return consumables_quantity;
    }

    public String getConsumables_notes() {
        return consumables_notes;
    }

    public String getConsumables_uom() {
        return consumables_uom;
    }

    public String getConsumables_current_stock() {
        return consumables_current_stock;
    }



    /*Aravinth 10-03-2016 at 11.20 am*/

    String trn_training_id;
    String trn_training_date;
    String trn_training_duration;

    public String getTrn_training_id() {
        return trn_training_id;
    }

    public void setTrn_training_id(String trn_training_id) {
        this.trn_training_id = trn_training_id;
    }

    public String getTrn_training_date() {
        return trn_training_date;
    }

    public void setTrn_training_date(String trn_training_date) {
        this.trn_training_date = trn_training_date;
    }

    public String getTrn_training_duration() {
        return trn_training_duration;
    }

    public void setTrn_training_duration(String trn_training_duration) {
        this.trn_training_duration = trn_training_duration;
    }

    public String getTrn_training_description() {
        return trn_training_description;
    }

    public void setTrn_training_description(String trn_training_description) {
        this.trn_training_description = trn_training_description;
    }

    public String getTrn_training_provideBy() {
        return trn_training_provideBy;
    }

    public void setTrn_training_provideBy(String trn_training_provideBy) {
        this.trn_training_provideBy = trn_training_provideBy;
    }

    public String getTrn_training_inVoice() {
        return trn_training_inVoice;
    }

    public void setTrn_training_inVoice(String trn_training_inVoice) {
        this.trn_training_inVoice = trn_training_inVoice;
    }

    String trn_training_description;
    String trn_training_provideBy;
    String trn_training_inVoice;

    String trn_wrnty_wrntyID;
    String trn_wrnty_startDate;

    public String getTrn_wrnty_wrntyID() {
        return trn_wrnty_wrntyID;
    }

    public void setTrn_wrnty_wrntyID(String trn_wrnty_wrntyID) {
        this.trn_wrnty_wrntyID = trn_wrnty_wrntyID;
    }

    public String getTrn_wrnty_startDate() {
        return trn_wrnty_startDate;
    }

    public void setTrn_wrnty_startDate(String trn_wrnty_startDate) {
        this.trn_wrnty_startDate = trn_wrnty_startDate;
    }

    public String getTrn_wrnty_endDate() {
        return trn_wrnty_endDate;
    }

    public void setTrn_wrnty_endDate(String trn_wrnty_endDate) {
        this.trn_wrnty_endDate = trn_wrnty_endDate;
    }

    public String getTrn_wrnty_desc() {
        return trn_wrnty_desc;
    }

    public void setTrn_wrnty_desc(String trn_wrnty_desc) {
        this.trn_wrnty_desc = trn_wrnty_desc;
    }

    public String getTrn_wrnty_duration() {
        return trn_wrnty_duration;
    }

    public void setTrn_wrnty_duration(String trn_wrnty_duration) {
        this.trn_wrnty_duration = trn_wrnty_duration;
    }

    public String getTrn_wrnty_type() {
        return trn_wrnty_type;
    }

    public void setTrn_wrnty_type(String trn_wrnty_type) {
        this.trn_wrnty_type = trn_wrnty_type;
    }

    String trn_wrnty_endDate;
    String trn_wrnty_desc;
    String trn_wrnty_duration;
    String trn_wrnty_type;
}
