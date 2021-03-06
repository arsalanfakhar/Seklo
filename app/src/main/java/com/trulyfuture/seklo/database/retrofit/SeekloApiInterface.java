package com.trulyfuture.seklo.database.retrofit;

import com.trulyfuture.seklo.models.CompanyHrResults;
import com.trulyfuture.seklo.models.CompanyResults;
import com.trulyfuture.seklo.models.CvExistsResults;
import com.trulyfuture.seklo.models.DegreeResults;
import com.trulyfuture.seklo.models.EducationResults;
import com.trulyfuture.seklo.models.EmploymentResults;
import com.trulyfuture.seklo.models.ExperienceResults;
import com.trulyfuture.seklo.models.HRServices;
import com.trulyfuture.seklo.models.HrResults;
import com.trulyfuture.seklo.models.JobApply;
import com.trulyfuture.seklo.models.JobsResults;
import com.trulyfuture.seklo.models.NotificationModel;
import com.trulyfuture.seklo.models.ResumeResults;
import com.trulyfuture.seklo.models.SekloResults;
import com.trulyfuture.seklo.models.ServicesResults;
import com.trulyfuture.seklo.models.SkillResults;
import com.trulyfuture.seklo.models.StudyFieldsResults;
import com.trulyfuture.seklo.models.UserResults;
import com.trulyfuture.seklo.models.Users;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SeekloApiInterface {

    @POST("users")
    Call<SekloResults> createUser(@Body Users user);

    @POST("users/login")
    Call<SekloResults> loginUser(@Body Users user);

    @POST("users/f-login")
    Call<SekloResults> facebookLoginUser(@Body Map<String,Object> fbUserMap);

    @GET("users/{id}")
    Call<UserResults> getUserById(@Path("id") int userId);

    @GET("company")
    Call<CompanyResults> getAllCompanies();

    @GET("company/{companyid}")
    Call<CompanyResults> getCompanyById(@Path("companyid") int companyid);

    @GET("hr")
    Call<HrResults> getAllHr();

    @PATCH("users/{id}")
    Call<SekloResults> updateUserDetails(@Body Users user, @Path("id") int userId);

    @PATCH("users/{id}")
    Call<SekloResults> updateUserImage(
            @Body Map<String, String> userMap,
            @Path("id") int userId
    );


    @GET("list-degree")
    Call<DegreeResults> getAllDegreesList();

    @GET("list-study")
    Call<StudyFieldsResults> getAllStudyFieldsList();

    @GET("user-ed/{id}")
    Call<EducationResults> getUserEducation(@Path("id") int userId);

    @POST("user-ed")
    Call<SekloResults> addUserEducation(@Body Map<String,Object> educationMap);

    @PATCH("user-ed/{id}")
    Call<SekloResults> updateUserEducation(@Path("id") int edId,@Body Map<String,Object> educationMap);

    @DELETE("user-ed/{id}")
    Call<SekloResults> removeUserEducation(@Path("id") int edId);

    @GET("listdata/list-type/1")
    Call<EmploymentResults> getAllEmploymentType();

    @POST("user-exp")
    Call<SekloResults> addUserExperience(@Body Map<String,Object> experienceMap);

    @GET("user-exp/{id}")
    Call<ExperienceResults> getUserExperience(@Path("id") int userId);

    @PATCH("user-exp/{id}")
    Call<SekloResults> updateUserExperience(@Path("id") int expId,@Body Map<String,Object> experienceMap);

    @DELETE("user-exp/{id}")
    Call<SekloResults> removeUserExperience(@Path("id") int expId);


    @GET("list-skills")
    Call<SkillResults> getAllSkills();

    @GET("user-skill/user/{id}")
    Call<SkillResults> getUserSkills(@Path("id") int userId);

    @POST("user-skill")
    Call<SekloResults> addUserSkill(@Body Map<String,Object> skillMap);

    @DELETE("user-skill/{id}")
    Call<SekloResults> deleteUserSkill(@Path("id") int skillId);

    @GET("jobs/company-jobs/{id}")
    Call<JobsResults> getCompanyJobs(@Path("id") int id);

    @GET("company-hr/{id}")
    Call<CompanyHrResults> getCompanyHr(@Path("id") int id);

    @GET("jobs/all-jobs/{userId}")
    Call<JobsResults> getAllJobs(@Path("userId") int userId);

    @GET("jobs/mobile-jobs")
    Call<JobsResults> getHomePageJobs();

    @GET("resume-upload/{userId}")
    Call<ResumeResults> getResumeById(@Path("userId") int userId);

    @POST("resume-upload")
    Call<SekloResults> addUserResume(@Body Map<String,Object> resumeMap);

    @PATCH("resume-upload/{userId}")
    Call<SekloResults> updateUserResume(@Path("userId") int userId,@Body Map<String,Object> resumeMap);


    @GET("service-data")
    Call<ServicesResults> getAllServices();

    @GET("service-data/{id}")
    Call<ServicesResults> getServiceById(@Path("id") int serviceId);

    @POST("resume-review")
    Call<SekloResults> addResumeReview(@Body HRServices hrService);

    @POST("resume-writing")
    Call<SekloResults> addResumeWriting(@Body HRServices hrService);

    @POST("career/android")
    Call<SekloResults> addCareer(@Body HRServices hrService);

    @POST("cover-letter")
    Call<SekloResults> addCoverLetter(@Body HRServices hrService);

    @POST("forgot-password")
    Call<SekloResults> resetPassword(@Body Map<String,Object> passMap);

    @GET("resume-upload/if-exist/{id}")
    Call<CvExistsResults> cvExists(@Path("id") int resumeId);

    @POST("applied-jobs")
    Call<SekloResults> applyJob(@Body JobApply jobApplyData);

    @GET("notifications/{id}")
    Call<NotificationModel> getAllNotifications(@Path("id") int userId) ;

    @GET("notifications/detail/{id}")
    Call<NotificationModel> getNotificationDetail(@Path("id") int notificationId);

}
