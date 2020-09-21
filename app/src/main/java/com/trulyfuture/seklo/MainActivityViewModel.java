package com.trulyfuture.seklo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.trulyfuture.seklo.models.CompanyResults;
import com.trulyfuture.seklo.models.DegreeResults;
import com.trulyfuture.seklo.models.EducationResults;
import com.trulyfuture.seklo.models.EmploymentResults;
import com.trulyfuture.seklo.models.ExperienceResults;
import com.trulyfuture.seklo.models.HRServices;
import com.trulyfuture.seklo.models.HrResults;
import com.trulyfuture.seklo.models.JobsResults;
import com.trulyfuture.seklo.models.ResumeResults;
import com.trulyfuture.seklo.models.SekloResults;
import com.trulyfuture.seklo.models.ServicesResults;
import com.trulyfuture.seklo.models.SkillResults;
import com.trulyfuture.seklo.models.StudyFieldsResults;
import com.trulyfuture.seklo.models.UserResults;
import com.trulyfuture.seklo.repository.SeekloRepository;
import com.trulyfuture.seklo.utils.SharedPreferenceClass;

public class MainActivityViewModel extends AndroidViewModel {

    private SeekloRepository seekloRepository;

    private int userId;

    public LiveData<HrResults> hrResults = new MutableLiveData<>();
    public LiveData<DegreeResults> degreeResults = new MutableLiveData<>();
    public LiveData<StudyFieldsResults> studyFieldsResults = new MutableLiveData<>();

    public LiveData<EmploymentResults> employmentResults = new MutableLiveData<>();


    public LiveData<SkillResults> skillResults = new MutableLiveData<>();


    public LiveData<CompanyResults> allCompanies = new MutableLiveData<>();
    public LiveData<JobsResults> allJobs = new MutableLiveData<>();
    public LiveData<JobsResults> homePageJobs = new MutableLiveData<>();

    public LiveData<ResumeResults> userResume = new MutableLiveData<>();

    public LiveData<ServicesResults> allSevices = new MutableLiveData<>();


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        seekloRepository = new SeekloRepository(application);
        SharedPreferenceClass sharedPreferenceClass = new SharedPreferenceClass(application.getApplicationContext(), SharedPreferenceClass.UserDetails);
        userId = sharedPreferenceClass.getInteger("userId");
        initialize();
    }

    private void initialize() {
        getCurrentUser();
        getAllHr();
        getAllDegrees();
        getAllStudyFields();
        getAllUserEducation(userId);
        getAllEmploymentType();
        getAllUserExperience(userId);
        getUserSkills(userId);
        getAllSkills();
        getAllCompanies();
        getAllJobs(userId);
        getHomeJobs();
        getUserResume(userId);
        getAllServices();
    }

    public int getUserId() {
        return this.userId;
    }

    public LiveData<UserResults> getCurrentUser() {
        return seekloRepository.getUserById(this.userId);
//
//        return seekloRepository.getUserById(id);
    }

    private void getAllHr() {
        hrResults = seekloRepository.getAllHr();
    }

    private void getAllDegrees() {
        degreeResults = seekloRepository.getAllDegrees();
    }

    private void getAllStudyFields() {
        studyFieldsResults = seekloRepository.getAllStudyFields();
    }

    public LiveData<EducationResults> getAllUserEducation(int id) {
        return seekloRepository.getUserEducation(id);
    }

    private void getAllEmploymentType() {
        employmentResults = seekloRepository.getAllEmploymentType();
    }

    public LiveData<ExperienceResults> getAllUserExperience(int id) {
        return seekloRepository.getUserExperience(id);
    }

    private void getAllSkills() {
        skillResults = seekloRepository.getAllSkillResults();
    }

    public LiveData<SkillResults> getUserSkills(int userId) {
        return seekloRepository.getUserSkillsList(userId);
    }

    private void getAllCompanies() {
        allCompanies = seekloRepository.getAllCompanies();
    }

    private void getAllJobs(int id) {
        allJobs = seekloRepository.getAllJobs(id);
    }

    private void getHomeJobs() {
        homePageJobs = seekloRepository.getHomePageJobs();
    }

    private void getUserResume(int id) {
        userResume = seekloRepository.getUserResume(id);
    }

    private void getAllServices() {
        allSevices = seekloRepository.getAllServices();
    }

    private LiveData<ServicesResults> getServiceById(int serviceId) {
        return seekloRepository.getServiceById(serviceId);
    }

    public LiveData<SekloResults> addResumeReview(HRServices hrServices) {
        return seekloRepository.addResumeReview(hrServices);
    }

    public LiveData<SekloResults> addResumeWriting(HRServices hrServices) {
        return seekloRepository.addResumeWriting(hrServices);
    }

    public LiveData<SekloResults> addCareerCounselling(HRServices hrServices) {
        return seekloRepository.addCareerCounselling(hrServices);
    }

    public LiveData<SekloResults> addCoverLetter(HRServices hrServices) {
        return seekloRepository.addCoverLetter(hrServices);
    }

}
