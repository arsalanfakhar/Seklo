package com.trulyfuture.seklo.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.trulyfuture.seklo.database.retrofit.RetrofitService;
import com.trulyfuture.seklo.database.retrofit.SeekloApiInterface;
import com.trulyfuture.seklo.models.CompanyResults;
import com.trulyfuture.seklo.models.DegreeResults;
import com.trulyfuture.seklo.models.EducationResults;
import com.trulyfuture.seklo.models.EmploymentResults;
import com.trulyfuture.seklo.models.ExperienceResults;
import com.trulyfuture.seklo.models.HrResults;
import com.trulyfuture.seklo.models.JobsResults;
import com.trulyfuture.seklo.models.SekloResults;
import com.trulyfuture.seklo.models.Results;
import com.trulyfuture.seklo.models.SkillResults;
import com.trulyfuture.seklo.models.StudyFieldsResults;
import com.trulyfuture.seklo.models.UserResults;
import com.trulyfuture.seklo.models.Users;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeekloRepository {

    private Application application;
    private SeekloApiInterface apiInterface;

    public SeekloRepository(Application application) {
        this.application = application;
        apiInterface = RetrofitService.getInterface();
    }

    public MutableLiveData<Results> createUser(Users user) {
        MutableLiveData<Results> data = new MutableLiveData<>();

        Call<SekloResults> resultsCall = apiInterface.createUser(user);

        resultsCall.enqueue(new Callback<SekloResults>() {
            @Override
            public void onResponse(Call<SekloResults> call, Response<SekloResults> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body().getResults());
                } else
                    Toast.makeText(application.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SekloResults> call, Throwable t) {
                Results result = new Results();
                result.setCode(-1);
                result.setMessage(t.getMessage());
                data.postValue(result);
            }
        });

        return data;
    }

    public MutableLiveData<Results> loginUser(Users user) {
        MutableLiveData<Results> data = new MutableLiveData<>();

        Call<SekloResults> resultsCall = apiInterface.loginUser(user);

        resultsCall.enqueue(new Callback<SekloResults>() {
            @Override
            public void onResponse(Call<SekloResults> call, Response<SekloResults> response) {
                if (response.isSuccessful() && response.body() != null)
                    data.postValue(response.body().getResults());
                else
                    Toast.makeText(application.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SekloResults> call, Throwable t) {
                Results result = new Results();
                result.setCode(-1);
                result.setMessage(t.getMessage());
                data.postValue(result);
            }
        });

        return data;
    }

    public MutableLiveData<UserResults> getUserById(int id) {
        MutableLiveData<UserResults> data = new MutableLiveData<>();

        Call<UserResults> userResultsCall = apiInterface.getUserById(id);

        userResultsCall.enqueue(new Callback<UserResults>() {
            @Override
            public void onResponse(Call<UserResults> call, Response<UserResults> response) {

                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else
                    Toast.makeText(application.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserResults> call, Throwable t) {
                UserResults results = new UserResults();
                results.setCode(-1);
                data.postValue(results);
            }
        });
        return data;
    }

    public MutableLiveData<SekloResults> updateUserDetails(Users users, int userId) {
        MutableLiveData<SekloResults> data = new MutableLiveData<>();

        Call<SekloResults> resultsCall = apiInterface.updateUserDetails(users, userId);

        resultsCall.enqueue(new Callback<SekloResults>() {
            @Override
            public void onResponse(Call<SekloResults> call, Response<SekloResults> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else {
                    Toast.makeText(application.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SekloResults> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return data;
    }

    public MutableLiveData<SekloResults> updateUserImage(Map<String, String> userMap, int userId) {
        MutableLiveData<SekloResults> data = new MutableLiveData<>();

        Call<SekloResults> resultsCall = apiInterface.updateUserImage(userMap, userId);

        resultsCall.enqueue(new Callback<SekloResults>() {
            @Override
            public void onResponse(Call<SekloResults> call, Response<SekloResults> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else {
                    Toast.makeText(application.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SekloResults> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return data;
    }

    public MutableLiveData<HrResults> getAllHr() {
        MutableLiveData<HrResults> data = new MutableLiveData<>();
        Call<HrResults> hrResultsCall = apiInterface.getAllHr();

        hrResultsCall.enqueue(new Callback<HrResults>() {
            @Override
            public void onResponse(Call<HrResults> call, Response<HrResults> response) {
                if (response.isSuccessful() && response.body() != null)
                    data.postValue(response.body());
                else
                    Toast.makeText(application.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<HrResults> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return data;
    }

    public MutableLiveData<DegreeResults> getAllDegrees() {
        MutableLiveData<DegreeResults> data = new MutableLiveData<>();

        Call<DegreeResults> degreeResultsCall = apiInterface.getAllDegreesList();

        degreeResultsCall.enqueue(new Callback<DegreeResults>() {
            @Override
            public void onResponse(Call<DegreeResults> call, Response<DegreeResults> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else
                    Toast.makeText(application.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DegreeResults> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return data;
    }

    public MutableLiveData<StudyFieldsResults> getAllStudyFields() {
        MutableLiveData<StudyFieldsResults> data = new MutableLiveData<>();

        Call<StudyFieldsResults> studyFieldsResultsCall = apiInterface.getAllStudyFieldsList();

        studyFieldsResultsCall.enqueue(new Callback<StudyFieldsResults>() {
            @Override
            public void onResponse(Call<StudyFieldsResults> call, Response<StudyFieldsResults> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else
                    Toast.makeText(application.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<StudyFieldsResults> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        return data;
    }

    public MutableLiveData<SekloResults> addUserEducation(Map<String, Object> educationMap) {

        MutableLiveData<SekloResults> data = new MutableLiveData<>();

        Call<SekloResults> resultsCall = apiInterface.addUserEducation(educationMap);

        resultsCall.enqueue(new Callback<SekloResults>() {
            @Override
            public void onResponse(Call<SekloResults> call, Response<SekloResults> response) {
                if (response.isSuccessful() && response.body() != null)
                    data.postValue(response.body());
                else
                    Toast.makeText(application.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SekloResults> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return data;
    }


    public MutableLiveData<EducationResults> getUserEducation(int userId) {

        MutableLiveData<EducationResults> data = new MutableLiveData<>();

        Call<EducationResults> resultsCall = apiInterface.getUserEducation(userId);

        resultsCall.enqueue(new Callback<EducationResults>() {
            @Override
            public void onResponse(Call<EducationResults> call, Response<EducationResults> response) {
                if (response.isSuccessful() && response.body() != null)
                    data.postValue(response.body());
                else
                    Toast.makeText(application.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<EducationResults> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return data;
    }

    public MutableLiveData<EmploymentResults> getAllEmploymentType() {

        MutableLiveData<EmploymentResults> data = new MutableLiveData<>();

        Call<EmploymentResults> employmentTypeCall = apiInterface.getAllEmploymentType();

        employmentTypeCall.enqueue(new Callback<EmploymentResults>() {
            @Override
            public void onResponse(Call<EmploymentResults> call, Response<EmploymentResults> response) {
                if (response.isSuccessful() && response.body() != null)
                    data.postValue(response.body());
                else
                    Toast.makeText(application.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<EmploymentResults> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return data;
    }

    public MutableLiveData<SekloResults> addUserExperience(Map<String, Object> experienceMap) {
        MutableLiveData<SekloResults> data = new MutableLiveData<>();

        Call<SekloResults> resultsCall = apiInterface.addUserExperience(experienceMap);

        resultsCall.enqueue(new Callback<SekloResults>() {
            @Override
            public void onResponse(Call<SekloResults> call, Response<SekloResults> response) {
                if (response.isSuccessful() && response.body() != null)
                    data.postValue(response.body());
                else
                    Toast.makeText(application.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SekloResults> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return data;
    }

    public MutableLiveData<ExperienceResults> getUserExperience(int userId) {
        MutableLiveData<ExperienceResults> data = new MutableLiveData<>();

        Call<ExperienceResults> resultsCall = apiInterface.getUserExperience(userId);

        resultsCall.enqueue(new Callback<ExperienceResults>() {
            @Override
            public void onResponse(Call<ExperienceResults> call, Response<ExperienceResults> response) {
                if (response.isSuccessful() && response.body() != null)
                    data.postValue(response.body());
                else
                    Toast.makeText(application.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ExperienceResults> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return data;
    }

    public MutableLiveData<SkillResults> getAllSkillResults() {

        MutableLiveData<SkillResults> data = new MutableLiveData<>();

        Call<SkillResults> skillResults = apiInterface.getAllSkills();

        skillResults.enqueue(new Callback<SkillResults>() {
            @Override
            public void onResponse(Call<SkillResults> call, Response<SkillResults> response) {
                if (response.isSuccessful() && response.body() != null)
                    data.postValue(response.body());
                else
                    Toast.makeText(application.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SkillResults> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return data;
    }

    public MutableLiveData<SkillResults> getUserSkillsList(int userId) {

        MutableLiveData<SkillResults> data = new MutableLiveData<>();

        Call<SkillResults> skillResults = apiInterface.getUserSkills(userId);

        skillResults.enqueue(new Callback<SkillResults>() {
            @Override
            public void onResponse(Call<SkillResults> call, Response<SkillResults> response) {
                if (response.isSuccessful() && response.body() != null)
                    data.postValue(response.body());
                else
                    Toast.makeText(application.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SkillResults> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return data;

    }

    public MutableLiveData<SekloResults> addUserSkills(Map<String, Object> skillMap) {

        MutableLiveData<SekloResults> data = new MutableLiveData<>();

        Call<SekloResults> resultsCall = apiInterface.addUserSkill(skillMap);


        resultsCall.enqueue(new Callback<SekloResults>() {
            @Override
            public void onResponse(Call<SekloResults> call, Response<SekloResults> response) {
                if (response.isSuccessful() && response.body() != null)
                    data.postValue(response.body());
                else
                    Toast.makeText(application.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SekloResults> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return data;
    }


    public MutableLiveData<CompanyResults> getAllCompanies() {

        MutableLiveData<CompanyResults> data = new MutableLiveData<>();
        Call<CompanyResults> companyResultsCall = apiInterface.getAllCompanies();

        companyResultsCall.enqueue(new Callback<CompanyResults>() {
            @Override
            public void onResponse(Call<CompanyResults> call, Response<CompanyResults> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else
                    Toast.makeText(application.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CompanyResults> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return data;
    }

    public MutableLiveData<CompanyResults> getCompanyById(int companyId) {

        MutableLiveData<CompanyResults> data = new MutableLiveData<>();
        Call<CompanyResults> companyByIdCall = apiInterface.getCompanyById(companyId);

        companyByIdCall.enqueue(new Callback<CompanyResults>() {
            @Override
            public void onResponse(Call<CompanyResults> call, Response<CompanyResults> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else
                    Toast.makeText(application.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<CompanyResults> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return data;
    }

    public MutableLiveData<JobsResults> getAllJobs(int userId) {
        MutableLiveData<JobsResults> data = new MutableLiveData<>();

        Call<JobsResults> jobsResultsCall = apiInterface.getAllJobs(userId);

        jobsResultsCall.enqueue(new Callback<JobsResults>() {
            @Override
            public void onResponse(Call<JobsResults> call, Response<JobsResults> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else
                    Toast.makeText(application.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JobsResults> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return data;
    }

}

