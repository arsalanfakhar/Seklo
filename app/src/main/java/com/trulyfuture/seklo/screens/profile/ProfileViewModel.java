package com.trulyfuture.seklo.screens.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.trulyfuture.seklo.models.CompanyResults;
import com.trulyfuture.seklo.models.DegreeResults;
import com.trulyfuture.seklo.models.SekloResults;
import com.trulyfuture.seklo.models.Users;
import com.trulyfuture.seklo.repository.SeekloRepository;

import java.util.Map;

public class ProfileViewModel extends AndroidViewModel {

    private SeekloRepository seekloRepository;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        seekloRepository = new SeekloRepository(application);
    }


    public LiveData<SekloResults> addUserEducation(Map<String, Object> educationMap) {
        return seekloRepository.addUserEducation(educationMap);
    }

    public LiveData<SekloResults> addUserExperience(Map<String, Object> experienceMap) {
        return seekloRepository.addUserExperience(experienceMap);
    }

    public LiveData<SekloResults> addUserSkill(Map<String, Object> skillMap) {
        return seekloRepository.addUserSkills(skillMap);
    }

    public LiveData<SekloResults> deleteUserSkill(int skillId){
        return seekloRepository.deleteUserSkill(skillId);
    }

    public LiveData<SekloResults> deleteEducation(int edId){
        return seekloRepository.removeEducation(edId);
    }

    public LiveData<SekloResults> deleteExperience(int expId){
        return seekloRepository.removeExperience(expId);
    }

    public LiveData<SekloResults> updateEducation(int edId,Map<String, Object> educationMap){
        return seekloRepository.updateEducation(edId,educationMap);
    }

    public LiveData<SekloResults> updateExperience(int expId,Map<String, Object> experienceMap){
        return seekloRepository.updateExperience(expId, experienceMap);
    }



}
