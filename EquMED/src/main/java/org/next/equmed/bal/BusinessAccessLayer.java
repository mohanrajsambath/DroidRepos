/* Copyright (C) Next Techno Entreprises, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Mohanraj.S <mohanraj.sambath@nexttechnosolutions.com>, December,2015
 */
package org.next.equmed.bal;

import android.database.sqlite.SQLiteDatabase;

import org.next.equmed.dal.DataAccessLayer;

import java.io.File;
import java.util.HashMap;

/**
 * Created by nextmoveo-1 on 2/12/15.
 */
public class BusinessAccessLayer {


    /**
     * @author – Mohanraj.S
     * @version – 0.1
     * @Updated_By Mohanraj.S
     * @Updated_On 02-12-2015 at 04:10:20 pm
     * @param – no parameters
     * @return – no return values
     * @throws – no exception throws
     * @description The Following parameter have declared as the
     * common Parameter that available in all the  Database Table and the ISACTIVE field  is Optional in few tables they are not implemented
     * @see – no link
     * @since – 02-12-2015 at 04:15:20 pm
     * @deprecated - no deprecation
     */


    public static String[] hospitalArray = null, hospitalLocation = null, hospitalNameAndLocation = null;
    public static HashMap<String, String> mHospitalHashMap = new HashMap<String, String>();
    public static HashMap<String, String> mHospitalHashMapByID = new HashMap<String, String>();
    public static HashMap<String, String> mHospitalLocationHashMap = new HashMap<String, String>();

    public static final String RESPONSE_SUCCESS = "NTE_01";
    public static final String RESPONSE_FAIL = "NTE_00";


    public static final String FLAG = "flag";
    public static final String ISACTIVE = "isactive";
    public static final String SYNC_STATUS = "sync_status";
    public static final String CREATED_BY = "created_by";
    public static final String CREATED_ON = "created_on";
    public static final String MODIFIED_BY = "modified_by";
    public static final String MODIFIED_ON = "modified_on";


    /**
     * @author – Mohanraj.S
     * @version – 0.1
     * @Updated_By Mohanraj.S
     * @Updated_On 02-12-2015 at 04:20:42 pm
     * @param – no parameters
     * @return – no return
     * @throws – no exception throws
     * @description Database Table Name.DB Path,DB Version,DB Name,
     * @see – no link
     * @since – 02-12-2015 at 04:20:41 pm
     * @deprecated - no deprecation
     */
///PHP

//    public static final String BASE_IP = "http://192.168.10.237";

//    public static final String BASE_IP = "http://192.168.10.155";

//
//    public static final String BASE_IP = "http://192.168.10.99";
//
////    public static final String BASE_IP = "http://equmed.nexttechnosolutions.com:8033";
//
//    public static final String BASE_URL = BASE_IP + "/EquMED/";
//    public static final String URL_MASTER_SYNC = BASE_URL + "synchronize.php";
//    public static final String URL_RETRIEVE = BASE_URL + "retrieve.php";
//    public static final String FILE_UPLOAD_URL = BASE_URL + "upload_files.php";
//    public static final String IMAGE_UPLOAD_URL = BASE_URL + "upload_images.php";

    //JAVA API

//    public static final String BASE_IP = "http://192.168.10.87:8080";
////    public static final String BASE_IP = "http://192.168.10.237:8080";
//
////    public static final String BASE_IP = "http://equmed.nexttechnosolutions.com:8033";
//
//    public static final String BASE_URL = BASE_IP + "/EquMED/";
//    public static final String URL_MASTER_SYNC = BASE_URL + "synchronize";
//    public static final String URL_RETRIEVE = BASE_URL + "retrieve";
//    public static final String FILE_UPLOAD_URL = BASE_URL + "upload_files";
//    public static final String IMAGE_UPLOAD_URL = BASE_URL + "upload_images";
//    public static final String ADMIN_LOGIN_URL = BASE_URL + "login";

    //ODOO API

//    login -> 192.168.10.89:9069/EquMED/Login
//    synchronize -> 192.168.10.89:9069/EquMED/synchronize
//    retrivel ->  192.168.10.89:9069/EquMED/retrieve
//    image upload-> 192.168.10.89:9069/EquMED/upload_images
//    document upload ->  192.168.10.89:9069/EquMED/upload_files

    public static  String BASE_IP = "";
//        public static  String BASE_IP = "http://192.168.10.87:8069";

//
////    public static final String BASE_IP = "http://equmed.nexttechnosolutions.com:8033";
//
//    public static final String BASE_URL = BASE_IP + "/EquMED/";
    public static final String BASE_URL =  "/EquMED/";
//    public static final String URL_MASTER_SYNC = BASE_URL + "synchronize";
        public static final String URL_MASTER_SYNC_ODOO = BASE_URL + "synchronize";
    public static final String URL_RETRIEVE = BASE_URL + "retrieve";
    public static final String FILE_UPLOAD_URL = BASE_URL + "upload_files";
    public static final String IMAGE_UPLOAD_URL = BASE_URL + "upload_images";
    public static final String ADMIN_LOGIN_URL = BASE_URL + "login";


    public static String mParetnRoleId = "";
    public static String mUserId = "1";
    public static String mAssigendHospitalId = "";

    public static String mPhoneNo = "+91-422-2971111";
    public static String mAboutusPhoneNo = "+675-325-7777";

    public static DataAccessLayer.DatabaseHelper mDbHelper;
    public static SQLiteDatabase mDb;
    public static final int DATABASE_VERSION = 1;
    public static String DATABASE_PATH = "data/data/org.next.equmed/databases/";
    public static final String EXPORT_DB_PATH = "/EquMED_DB/";
    public static final String DATABASE_NAME = "EquMED.db";
    public static final String DB_TABLE_MST_EQUIPMENT_STATUS = "mst_equipment_status";
    public static final String DB_TABLE_MST_HOSPITAL_ENROLL = "mst_hospital_enroll";
    public static final String DB_TABLE_TRN_USER_REGISTRATION = "trn_user_registration";
    public static final String DB_TABLE_TRN_EQUIPMENT_ENROLL = "trn_equipment_enroll";
    public static final String DB_TABLE_TRN_INSTALLATION_ENROLL = "trn_installation_enroll";
    public static final String DB_TABLE_TRN_EQUIPMENT_ENROLL_ACCESSORIES = "trn_equipment_enroll_accessories";
    public static final String DB_TABLE_TRN_IMAGES = "trn_images";
    public static final String DB_TABLE_TRN_DOCUMENTS = "trn_documents";
    public static final String DB_TABLE_TRN_CONSUMABLES = "trn_consumables";
    public static final String DB_TABLE_TRN_VOICE_OF_CUSTOMER = "trn_voice_of_customer";
    public static final String DB_TABLE_TRN_WARRANTY_DETAILS = "trn_warranty_details";
    public static final String DB_TABLE_TRN_SERVICE_DETAILS = "trn_service_details";
    public static final String DB_TABLE_TRN_TRAINING_DETAILS = "trn_training_details";


    public static final String SYNC_STATUS_VALUE = "0";
    public static final String FLAG_VALUE = "0";
    public static final String IS_ACTIVE_VALUE = "Y";
    public static final String DEFAULT_UTC = "0000000000000";
    public static final int PICK_FROM_CAMERA = 1;
    public static final int PICK_FROM_GALLERY = 2;
    public static final int FILE_ATTACHMENT = 3;
    public static final int CROP_IMAGE = 4;
    public static final int BAR_CODE_SCAN = 5;
    public static final String TAG = "EquMED";

    public static String versionCode = "V 2.0";
    public static String versionDate = "01-Apr-2016";
    public static String versionExpiryDate = "31-Mar-2017";
    public static String versionBuildNumber = "01_04_2016_B_0.1";

    public static final String EXIT_MESSAGE = "Do you want to exit?";
    public static final String LOGOUT_MESSAGE = "Do you want to logout?";
    public static final String DELETE_MESSAGE = "Do you want to delete?";
    public static final String SAVE_MESSAGE = "Do you want to save?";
    public static final String LOGIN_MESSAGE = "You have been logged-in successfully";


    public static final String USER_CREATE_MESSAGE = "User Created Successfully";
    public static final String USER_UPDATE_MESSAGE = "User Updated Successfully";
    public static final String USER_DELETE_MESSAGE = "User Deleted Successfully";

    public static final String PROJECT_NAME = "EquMED";


    public static boolean IS_INTERNETAVAILABLE, IS_DATASYNC;
    public static final String LAST_SYNC_DATE = "last_sync_date";
    public static final String DATA = "data";
    public static File myExternalFile;


    public static int MEDICAL_EQUIPMENT_FLAG = 0;
    public static int UPDATE_GOBACK_FLAG = 0;
    public static String VIEW_MEDICAL_EQUIPMENT_ENROLL_ID = "";
    public static String VIEW_MEDICAL_HOSPITAL_ID = "";
    public static String VIEW_MEDICAL_EQUIPMENT_ID = "";


    public static String MEDICALEQUIPMENT_IMAGE = "M";
    public static String EQUIPMENT_IMAGE = "EQ_I";
    public static String MEDICALEQUIPMENT_DOCUMENT = "MD";
    public static String INSTALLDETAILS_INSTALLER_IMG = "ID_I";
    public static String INSTALLDETAILS_INSTALLER_DOCUMENT = "ID_ID";
    public static String INSTALLDETAILS_OWNER_IMAGE = "ID_O";
    public static String INSTALLDETAILS_OWNER_DOCUMENT = "ID_OD";
    public static String TRAININGDETAILS_IMAGE = "TD_I";
    public static String TRAININGDETAILS_DOCUMENT = "TD_D";
    public static String SERVICEDETAIL_IMAGE = "SD_I";
    public static String SERVICEDETAIL_DOCUMENT = "SD_D";
    public static String WARRANTYDETAILS_DOCUMENT = "WD_D";
    public static boolean editPage = false;


    public static String IS_ACTIVE_STATUS_SUCCESS = "Y";
    public static String IS_ACTIVE_STATUS_FAIL = "N";

    public static String STATUS_ACTIVE = "Active";
    public static String STATUS_INACTIVE = "In-Active";
    public static String bug_class_name = "";


    /**
     * @author – Mohanraj.S
     * @version – 0.1
     * @Updated_By Mohanraj.S
     * @Updated_On 02-12-2015 at 04:25:32 pm
     * @param – no parameters
     * @return – no return values
     * @throws – no exception throws
     * @description The MST_EQUIPMENT_STATUS table that have to maintain
     * the status of the Enrolled Device
     * @see – Its Link with the Common Parameter Declaration
     * @since – 02-12-2015 at 04:25:32 pm
     * @deprecated - no deprecation
     */
    public static final String EQ_ID = "eq_id";
    public static final String EQ_NAME = "eq_name";
    public static final String IS_STANDARD = "is_standard";

    /**
     * @author – Mohanraj.S
     * @version – 0.1
     * @Updated_By Mohanraj.S
     * @Updated_On 02-12-2015 at 03:35:57 pm
     * @param – no parameters
     * @return – no return values
     * @throws – no exception throws
     * @description The TRN_IMAGES table that have to maintain
     * the image data of the hospital,Equipment Enrollment,Installation Enrollment
     * @see – Its Link with the Common Parameter Declaration
     * @since – 08-12-2015 at 03:35:56 pm
     * @deprecated - no deprecation
     */
    public static final String IMG_IMG_ID = "img_id";
    public static final String IMG_ID = "id";
    public static final String IMG_NAME = "image_name";
    public static final String IMG_ENCRYPTED_DATA = "encrypted_image";
    //public static final String FLAG = "flag";

    /**
     * @author – Mohanraj.S
     * @version – 0.1
     * @Updated_By Mohanraj.S
     * @Updated_On 02-12-2015 at 03:39:40 pm
     * @param – no parameters
     * @return – no return values
     * @throws – no exception throws
     * @description The TRN_IMAGES table that have to maintain
     * the image data of the hospital,Equipment Enrollment,Installation Enrollment
     * @see – Its Link with the Common Parameter Declaration
     * @since – 08-12-2015 at 03:39:26 pm
     * @deprecated - no deprecation
     */
    public static final String DOC_DOC_ID = "doc_id";
    public static final String DOC_ID = "id";
    public static final String DOC_NAME = "document_name";
    public static final String DOC_ENCRYPTED_DATA = "encrypted_document";
    //public static final String FLAG = "flag";
    public static final String DOC_TYPE = "document_type";

    /**
     * @author – Mohanraj.S
     * @version – 0.1
     * @Updated_By Mohanraj.S
     * @Updated_On 02-12-2015 at 04:25:06 pm
     * @param – no parameters
     * @return – no return values
     * @throws – no exception throws
     * @description The MST_HOSPITAL_ENROLL table that have to maintain
     * the particulars of the Enrolled Hospital
     * @see – Its Link with the Common Parameter Declaration
     * @since – 02-12-2015 at 04:25:06 pm
     * @deprecated - no deprecation
     */
    public static final String HOSPITAL_ID = "hospital_id";
    public static final String HOSPITAL_NAME = "hospital_name";
    public static final String HOSPITAL_LOCATION = "hospital_location";
    public static final String HOSPITAL_DESC = "hospital_desc";
    public static final String HOSPITAL_ADDRESS1 = "hospital_address1";
    public static final String HOSPITAL_ADDRESS2 = "hospital_address2";
    public static final String HOSPITAL_ADDRESS3 = "hospital_address3";
    public static final String HOSPITAL_STATE = "hospital_state";
    public static final String HOSPITAL_COUNTRY = "hospital_country";
    public static final String HOSPITAL_PHNO1 = "hospital_phno1";
    public static final String HOSPITAL_PHNO2 = "hospital_phno2";
    public static final String HOSPITAL_EMAIL = "hospital_email";
    public static final String HOSPITAL_NOTES = "hospital_notes";
    public static final String STANDARD_EQUIPMENTS = "standard_equipments";


    /**
     * @author – Mohanraj.S
     * @version – 0.1
     * @Updated_By Mohanraj.S
     * @Updated_On 02-12-2015 at 04:30:56 pm
     * @param – no parameters
     * @return – no return values
     * @throws – no exception throws
     * @description The TRN_USER_REGISTRATION table that have to maintain
     * the particulars of the Enrolled Hospital
     * @see – Its Link with the Common Parameter Declaration
     * @since – 02-12-2015 at 04:30:56 pm
     * @deprecated - no deprecation
     */
    public static final String USER_ID = "user_id";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_FIRST_NAME = "user_first_name";
    public static final String USER_LAST_NAME = "user_last_name";
    public static final String USER_PHONENO = "user_phoneno";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_IMAGE = "user_image";
    public static final String USER_ADMIN = "user_admin";
    public static final String USER_HOSPITAL = "user_hospital";
    public static final String USER_EFFECT_STARTDATE = "user_effect_startdate";
    public static final String USER_EFFECT_ENDDATE = "user_effect_enddate";


    /**
     * @author – Mohanraj.S
     * @version – 0.1
     * @Updated_By Mohanraj.S
     * @Updated_On 02-12-2015 at 04:30:20 pm
     * @param – no parameters
     * @return – no return values
     * @throws – no exception throws
     * @description The TRN_EQUIPMENT_ENROLL table that have to maintain
     * the particulars of the Enrolled Hospital
     * @see – Its Link with the Common Parameter Declaration
     * @since – 02-12-2015 at 04:32:20 pm
     * @deprecated - no deprecation
     */
    public static final String EQ_ENROLL_ID = "eq_enroll_id";
    //public static final String EQ_ID = "eq_id";
    public static final String EQ_HOSPITAL_ID = "eq_hospital_id";
    public static final String EQ_LOCATION_CODE = "eq_location_code";
    public static final String GPS_COORDINATES = "gps_coordinates";
    public static final String EQ_SERIALNO = "eq_serialno";
    //public static final String EQ_NAME = "eq_name";
    public static final String EQ_MAKE = "eq_make";
    public static final String EQ_MODEL = "eq_model";
    public static final String EQ_INSTALL_DATE = "eq_install_date";
    public static final String EQ_SERVICE_TAGNO = "eq_service_tagno";
    public static final String EQ_NOTES = "eq_notes";
    public static final String EQ_EXTRA_ACCESSORIES = "eq_extra_accessories";
    public static final String EQ_INSTALL_STATUS = "eq_install_status";
    public static final String EQ_INSTALL_NOTES = "eq_install_notes";
    public static final String EQ_WORKING_STATUS = "eq_working_condition";
    public static final String EQ_WORKING_NOTES = "eq_working_notes";


    /**
     * @author – Mohanraj.S
     * @version – 0.1
     * @Updated_By Mohanraj.S
     * @Updated_On 07-12-2015 at 11:32:20 Am
     * @param – no parameters
     * @return – no return values
     * @throws – no exception throws
     * @description The TRN_EQUIPMENT_ENROLL table that have to maintain
     * the particulars of the Enrolled Hospital
     * @see – Its Link with the Common Parameter Declaration
     * @since – 07-12-2015 at 11:32:20 Am
     * @deprecated - no deprecation
     */
    //public static final String EQ_ENROLL_ID = "eq_enroll_id";
    public static final String EQ_ENROLL_UPS = "eq_enroll_ups";
    public static final String EQ_ENROLL_MANUAL = "eq_enroll_manual";
    public static final String EQ_ENROLL_STABILIZER = "eq_enroll_stabilizer";
    public static final String EQ_ENROLL_OTHERS = "eq_enroll_others";


    /**
     * @author – Mohanraj.S
     * @version – 0.1
     * @Updated_By Mohanraj.S
     * @Updated_On 02-12-2015 at 04:50:57 pm
     * @param – no parameters
     * @return – no return values
     * @throws – no exception throws
     * @description The TRN_INSTALLATION_ENROLL table that have to maintain
     * the particulars of the Enrolled Hospital
     * @see – Its Link with the Common Parameter Declaration
     * @since – 02-12-2015 at 04:50:56 pm
     * @deprecated - no deprecation
     */
    public static final String INST_EQUIPMENT_ENROLL_ID = "inst_equipment_enroll_id";
    public static final String INST_COMPANY_BY = "inst_company_by";
    public static final String INST_ENGG_NAME = "inst_engg_name";
    public static final String INST_INSTALL_DATE = "inst_install_date";
    public static final String INST_LOCATION = "inst_location";
    public static final String INST_NEAR_BY_PHONENO = "inst_near_by_phoneno";
    public static final String INST_NOTES = "inst_notes";
    public static final String INST_AREA = "inst_area";
    public static final String INST_OWNER_NAME = "inst_owner_name";
    public static final String INST_OWNER_PHONENO = "inst_owner_phoneno";



    /**
     * @author – Muralidharan M
     * @version – 2.0
     * @Updated_By Muralidharan M
     * @Updated_On 09-03-2016 at 08:26:57 am
     * @param – no parameters
     * @return – no return values
     * @throws – no exception throws
     * @description The TRN_CONSUMABLES table that have to maintain
     * the particulars of the Enrolled Consumables
     * @see – Its Link with the Common Parameter Declaration
     * @since – 09-03-2016 at 08:26:57 pm
     * @deprecated - no deprecation
     */

    public static final String CONSUMABLES_ID = "consumables_id";
    public static final String CONSUMABLES_EQUIPMENT_ENROLL_ID = "consumables_equipment_enroll_id";
    public static final String CONSUMABLES_HOSPITAL_ID = "consumables_hospital_id";
    public static final String CONSUMABLES_EQUIPMENT_ID = "consumables_equipment_id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String TYPE_OF_USAGE = "type_of_usage";
    public static final String USAGE_PARAMETER = "usage_parameter";
    public static final String QUANTITY = "quantity";
    public static final String CONSUMABLES_UOM = "consumables_uom";
    public static final String CONSUMABLES_CURRENT_STOCK = "consumables_current_stock";
    public static final String CONSUMABLE_NOTES = "consumable_notes";


    /**
     * @author – Muralidharan M
     * @version – 2.0
     * @Updated_By Muralidharan M
     * @Updated_On 09-03-2016 at 08:40:57 am
     * @param – no parameters
     * @return – no return values
     * @throws – no exception throws
     * @description The TRN_WARRANTY_DETAILS table that have to maintain
     * the particulars of the Enrolled Warranty Details
     * @see – Its Link with the Common Parameter Declaration
     * @since – 09-03-2016 at 08:40:57 pm
     * @deprecated - no deprecation
     */

    public static final String WARRANTY_ID = "warranty_id";
    public static final String WARRANTY_EQUIPMENT_ENROLL_ID = "warranty_equipment_enroll_id";
    public static final String WARRANTY_HOSPITAL_ID = "warranty_hospital_id";
    public static final String WARRANTY_EQUIPMENT_ID = "warranty_equipment_id";
    public static final String WARRANTY_START_DATE = "warranty_start_date";
    public static final String WARRANTY_END_DATE = "warranty_end_date";
    public static final String WARRANTY_DURATION = "warranty_duration";
    public static final String WARRANTY_DESCRIPTION = "warranty_description";
    public static final String WARRANTY_TYPE = "warranty_type";

    /**
     * @author – Muralidharan M
     * @version – 2.0
     * @Updated_By Muralidharan M
     * @Updated_On 09-03-2016 at 08:45:57 am
     * @param – no parameters
     * @return – no return values
     * @throws – no exception throws
     * @description The TRN_VOICE_OF_CUSTOMER table that have to maintain
     * the particulars of the Enrolled Voice of Customer
     * @see – Its Link with the Common Parameter Declaration
     * @since – 09-03-2016 at 08:45:57 pm
     * @deprecated - no deprecation
     */

    public static final String VOC_ID = "voc_id";
    public static final String VOC_EQUIPMENT_ENROLL_ID = "voc_equipment_enroll_id";
    public static final String TYPE = "type";
    public static final String IN_BRIEF = "in_brief";
    public static final String VOC_HOSPITAL_ID = "voc_hospital_id";
    public static final String VOC_EQUIPMENT_ID = "voc_equipment_id";


    /**
     * @author – Muralidharan M
     * @version – 2.0
     * @Updated_By Muralidharan M
     * @Updated_On 09-03-2016 at 08:50:57 am
     * @param – no parameters
     * @return – no return values
     * @throws – no exception throws
     * @description The TRN_SERVICE_DETAILS table that have to maintain
     * the particulars of the Enrolled Service Details
     * @see – Its Link with the Common Parameter Declaration
     * @since – 09-03-2016 at 08:50:57 pm
     * @deprecated - no deprecation
     */

    public static final String SERVICE_ID = "service_id";
    public static final String SERVICE_EQUIPMENT_ENROLL_ID = "service_equipment_enroll_id";
    public static final String SERVICE_HOSPITAL_ID = "service_hospital_id";
    public static final String SERVICE_EQUIPMENT_ID = "service_equipment_id";
    public static final String SERVICE_DATE_TIME = "service_date_time";
    public static final String SERVICE_DURATION = "service_duration";
    public static final String SERVICE_TYPE = "service_type";
    public static final String SERVICE_NOTES = "service_notes";
    public static final String SERVICE_APPROVED_BY = "service_approved_by";
    public static final String SERVICE_INVOICE = "service_invoice";
    public static final String SERVICED_BY = "serviced_by";

    /**
     * @author – Muralidharan M
     * @version – 2.0
     * @Updated_By Muralidharan M
     * @Updated_On 09-03-2016 at 08:55:57 am
     * @param – no parameters
     * @return – no return values
     * @throws – no exception throws
     * @description The TRN_TRAINING_DETAILS table that have to maintain
     * the particulars of the Enrolled Training Details
     * @see – Its Link with the Common Parameter Declaration
     * @since – 09-03-2016 at 08:55:57 pm
     * @deprecated - no deprecation
     */

    public static final String TRAINING_ID = "training_id";
    public static final String TRAINING_EQUIPMENT_ENROLL_ID = "training_equipment_enroll_id";
    public static final String TRAINING_HOSPITAL_ID = "training_hospital_id";
    public static final String TRAINING_EQUIPMENT_ID = "training_equipment_id";
    public static final String TRAINING_DATE = "training_date";
    public static final String TRAINING_DURATION = "training_duration";
    public static final String TRAINING_DESCRIPTION = "training_description";
    public static final String TRAINING_PROVIDED_BY = "training_provided_by";
    public static final String TRAINING_INVOICE = "training_invoice";


    /**
     * @param – email is string parameter
     * @return – Boolean Value
     * @throws – no exception throws
     * @author – Mohanraj.S
     * @version – 0.1
     * @Updated_By Mohanraj.S
     * @Updated_On 04-12-2015 at 11:04:33 am
     * @description The isEmailValid is the static method that should used for global access to validate the Email
     * @see – No Links
     * @since – 04-12-2015 at 11:04:33 am
     * @deprecated - no deprecation
     */


    // validating password with retype password
    public static boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }


}
