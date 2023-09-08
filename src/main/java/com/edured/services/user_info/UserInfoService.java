package com.edured.services.user_info;

import com.edured.model.userInfo.AboutUser;
import com.edured.model.userInfo.UserEducation;
import com.edured.model.userInfo.UserWorkExperience;
import com.edured.model.users.EduredUser;
import com.edured.model.users.Student;
import com.edured.model.users.Teacher;
import com.edured.repository.user_info.AboutUserRepository;
import com.edured.repository.user_info.UserEducationRepository;
import com.edured.repository.user_info.UserWorkExperienceRepository;
import com.edured.services.users.EduredUserService;
import com.edured.services.users.StudentService;
import com.edured.services.users.TeacherService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    @Autowired
    private AboutUserRepository aboutUserRepository;
    @Autowired
    private UserEducationRepository userEducationRepository;
    @Autowired
    private UserWorkExperienceRepository userWorkExperienceRepository;
    @Autowired
    private EduredUserService userService;

    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    public AboutUser saveAboutUser(AboutUser aboutUser, Principal principal) {
        EduredUser user = userService.getLoggedInUser(principal);
        if (user.getRole().equals("ROLE_TEACHER")) {
            Teacher teacher = teacherService.findByUserId(user.getId());
            teacher.setAboutUser(aboutUser);
            teacherService.addTeacher(teacher);
        }
        // else if(user.getRole().equals("ROLE_STUDENT")){
        // Student student = studentService.findByUserId(user.getId());
        // }
        return null;
    }

    public AboutUser saveAboutUser(AboutUser aboutUser) {
        return aboutUserRepository.save(aboutUser);
    }

    public void saveUserEducation(UserEducation education, Principal principal) {
        EduredUser user = userService.getLoggedInUser(principal);
        if (user.getRole().equals("ROLE_TEACHER")) {
            Teacher teacher = teacherService.findByUserId(user.getId());
            education.setAboutUser(teacher.getAboutUser());
            userEducationRepository.save(education);
        }
        // else if(user.getRole().equals("ROLE_STUDENT")){
        // Student student = studentService.findByUserId(user.getId());
        // }
    }

    public void updateUserInfo(HttpServletRequest request, EduredUser user) {
        String name = request.getParameter("name");
        String headline = request.getParameter("headline");
        String githubUrl = request.getParameter("githubUrl");
        String linkdInUrl = request.getParameter("linkdInUrl");
        String websiteUrl = request.getParameter("websiteUrl");
        AboutUser aboutUser = new AboutUser(0, headline, githubUrl, linkdInUrl, websiteUrl, null, null, null);
        if (user.getRole().equalsIgnoreCase("role_teacher")) {
            Teacher teacher = teacherService.findByUserId(user.getId());
            AboutUser ab = teacher.getAboutUser();
            if (ab == null) {
                teacher.setAboutUser(aboutUser);
            } else {
                ab.setSkills(headline);
                ab.setGithubUrl(githubUrl);
                ab.setLinkedinUrl(linkdInUrl);
                ab.setWebsiteUrl(websiteUrl);
                teacher.setAboutUser(ab);
            }
            teacher.getUser().setName(name);
            teacherService.addTeacher(teacher);
        } else if (user.getRole().equalsIgnoreCase("role_student")) {
            Student student = studentService.findByUserId(user.getId());
            AboutUser ab = student.getAboutUser();
            if (ab == null) {
                student.setAboutUser(aboutUser);
            } else {
                ab.setSkills(headline);
                ab.setGithubUrl(githubUrl);
                ab.setLinkedinUrl(linkdInUrl);
                ab.setWebsiteUrl(websiteUrl);
                student.setAboutUser(ab);
            }
            student.getUser().setName(name);
            studentService.addStudent(student);
        }
    }

    public void updateUserAbout(HttpServletRequest request, EduredUser user) {
        String about = request.getParameter("about");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String college = request.getParameter("education");
        String worksAt = request.getParameter("worksAt");
        if (user.getRole().equalsIgnoreCase("role_teacher")) {
            Teacher teacher = teacherService.findByUserId(user.getId());
            AboutUser aboutUser = teacher.getAboutUser();
            if (aboutUser != null) {
                aboutUser.setAbout(about);
                List<UserEducation> educations = aboutUser.getUserEducations();
                List<UserWorkExperience> workExperiences = aboutUser.getUserWorkExperiences();
                if (educations == null || educations.isEmpty()) {
                    UserEducation education = new UserEducation(0, college, null, null, null, startDate, endDate, null,
                            aboutUser);
                    educations.add(education);
                    aboutUser.setUserEducations(educations);
                } else if (educations != null || !educations.isEmpty()) {
                    UserEducation education = educations.get(0);
                    education.setSchoolName(college);
                    education.setEndDate(endDate);
                    educations.set(0, education);
                    aboutUser.setUserEducations(educations);
                }
                if (workExperiences == null || workExperiences.isEmpty()) {
                    UserWorkExperience workExperience = new UserWorkExperience(0, worksAt, null, null, null, null,
                            aboutUser);
                    workExperiences.add(workExperience);
                    aboutUser.setUserWorkExperiences(workExperiences);
                } else if (workExperiences != null || !workExperiences.isEmpty()) {
                    UserWorkExperience workExperience = workExperiences.get(0);
                    workExperience.setCompanyName(worksAt);
                    workExperiences.set(0, workExperience);
                    aboutUser.setUserWorkExperiences(workExperiences);
                }
                teacher.setAboutUser(aboutUser);
                teacherService.addTeacher(teacher);
            }
            if (aboutUser == null) {
                aboutUser = new AboutUser(0, about, null, null, null, null, null, null);
                List<UserEducation> educations = new ArrayList<>();
                List<UserWorkExperience> workExperiences = new ArrayList<>();
                UserEducation education = new UserEducation(0, college, null, null, null, startDate, endDate, null,
                        aboutUser);
                educations.add(education);
                UserWorkExperience workExperience = new UserWorkExperience(0, worksAt, null, null, null, null,
                        aboutUser);
                workExperiences.add(workExperience);
                aboutUser.setUserEducations(educations);
                aboutUser.setUserWorkExperiences(workExperiences);
                teacher.setAboutUser(aboutUser);
                teacherService.addTeacher(teacher);
            }
        } else if (user.getRole().equalsIgnoreCase("role_student")) {
            Student student = studentService.findByUserId(user.getId());
            AboutUser aboutUser = student.getAboutUser();
            if (aboutUser != null) {
                aboutUser.setAbout(about);
                List<UserEducation> educations = aboutUser.getUserEducations();
                List<UserWorkExperience> workExperiences = aboutUser.getUserWorkExperiences();
                if (educations == null || educations.isEmpty()) {
                    UserEducation education = new UserEducation(0, college, null, null, null, startDate, endDate, null,
                            aboutUser);
                    educations.add(education);
                    aboutUser.setUserEducations(educations);
                } else if (educations != null || !educations.isEmpty()) {
                    UserEducation education = educations.get(0);
                    education.setSchoolName(college);
                    education.setEndDate(endDate);
                    educations.set(0, education);
                    aboutUser.setUserEducations(educations);
                }
                if (workExperiences == null || workExperiences.isEmpty()) {
                    UserWorkExperience workExperience = new UserWorkExperience(0, worksAt, null, null, null, null,
                            aboutUser);
                    workExperiences.add(workExperience);
                    aboutUser.setUserWorkExperiences(workExperiences);
                } else if (workExperiences != null || !workExperiences.isEmpty()) {
                    UserWorkExperience workExperience = workExperiences.get(0);
                    workExperience.setCompanyName(worksAt);
                    workExperiences.set(0, workExperience);
                    aboutUser.setUserWorkExperiences(workExperiences);
                }
                student.setAboutUser(aboutUser);
                studentService.addStudent(student);
            }
            System.out.println(aboutUser);
            if (aboutUser == null) {
                System.out.println("About user is null");
                aboutUser = new AboutUser();
                aboutUser.setAbout(about);
                List<UserEducation> educations = new ArrayList<>();
                UserEducation education = new UserEducation(0, college, null, null, null, startDate, endDate, null,
                        aboutUser);
                educations.add(education);
                aboutUser.setUserEducations(educations);
                List<UserWorkExperience> workExperiences = new ArrayList<>();
                UserWorkExperience workExperience = new UserWorkExperience(0, worksAt, null, null, null, null,
                        aboutUser);
                workExperiences.add(workExperience);
                aboutUser.setUserWorkExperiences(workExperiences);
                student.setAboutUser(aboutUser);
                studentService.addStudent(student);

            }
        }
    }

    public void updateUserWorkExperience(UserWorkExperience userWorkExperience, Principal principal) {
        long id = userWorkExperience.getId();
        String companyName = userWorkExperience.getCompanyName();
        String position = userWorkExperience.getPosition();
        String startDate = userWorkExperience.getStartDate();
        String endDate = userWorkExperience.getEndDate();
        String description = userWorkExperience.getDescription();
        // get login user
        // user se about user get kro
        // Step 1: Get the user object by email
        EduredUser user = userService.getLoggedInUser(principal);

        // Step 2: Create a new UserWorkExperience object
        UserWorkExperience workExperience = new UserWorkExperience(
                id, companyName, position, startDate, endDate, description, null // aboutUser is initially null
        );
        AboutUser aboutUser = null;
        // Step 3: Check the role of the user
        System.out.println(user.getRole());
        if (user.getRole().equalsIgnoreCase("role_teacher")) {
            // Step 4: Retrieve the AboutUser associated with the teacher
            Teacher teacher = teacherService.findByUserId(user.getId());
            aboutUser = teacher.getAboutUser();
            if(aboutUser == null) {
                aboutUser = new AboutUser();
                teacher.setAboutUser(aboutUser);
                aboutUser = teacherService.addTeacher(teacher).getAboutUser();
            }
        } else if (user.getRole().equalsIgnoreCase("role_student")) {
            // Step 4: Retrieve the AboutUser associated with the teacher
            Student student = studentService.findByUserId(user.getId());
            aboutUser = student.getAboutUser();
            if(aboutUser == null) {
                aboutUser = new AboutUser();
                student.setAboutUser(aboutUser);
                aboutUser = studentService.addStudent(student).getAboutUser();
            }
        }
        if (aboutUser != null) {
            // Step 5: Update an existing UserWorkExperience or create a new one
            List<UserWorkExperience> experiences = aboutUser.getUserWorkExperiences();
            boolean found = false;
            if(experiences == null) {
                experiences = new ArrayList<>();
            }
            for (int i = 0; i < experiences.size(); i++) {
                UserWorkExperience e = experiences.get(i);
                if (e.getId() == workExperience.getId()) {
                    workExperience.setAboutUser(aboutUser);
                    experiences.set(i, workExperience);
                    found = true;
                    break;
                }
            }
            if (!found) {
                workExperience.setAboutUser(aboutUser);
                experiences.add(workExperience);
            }

            // Step 6: Save the changes to the AboutUser object
            aboutUser.setUserWorkExperiences(experiences);
            saveAboutUser(aboutUser);
        }
    }

    public void updateUserEducation(UserEducation userEducation, Principal principal) {
        EduredUser user = userService.getLoggedInUser(principal);
        userEducation.setAboutUser(null);
        AboutUser aboutUser = null;
        if (user.getRole().equalsIgnoreCase("role_teacher")) {
            Teacher teacher = teacherService.findByUserId(user.getId());
            aboutUser = teacher.getAboutUser();
            if(aboutUser == null) {
                aboutUser = new AboutUser();
                teacher.setAboutUser(aboutUser);
                aboutUser = teacherService.addTeacher(teacher).getAboutUser();
            }
        } else if (user.getRole().equalsIgnoreCase("role_student")) {
            Student student = studentService.findByUserId(user.getId());
            aboutUser = student.getAboutUser();
            if(aboutUser == null) {
                aboutUser = new AboutUser();
                student.setAboutUser(aboutUser);
                aboutUser = studentService.addStudent(student).getAboutUser();
            }
        }
        if (aboutUser != null) {
            List<UserEducation> educations = aboutUser.getUserEducations();
            boolean found = false;
            if(educations == null) {
                educations = new ArrayList<>();
            }
            for (int i = 0; i < educations.size(); i++) {
                UserEducation e = educations.get(i);
                if (e.getId() == userEducation.getId()) {
                    userEducation.setAboutUser(aboutUser);
                    educations.set(i, userEducation);
                    found = true;
                    break;
                }
            }
            if (!found) {
                userEducation.setAboutUser(aboutUser);
                educations.add(userEducation);
            }
            aboutUser.setUserEducations(educations);
            saveAboutUser(aboutUser);
        }
    }

}
