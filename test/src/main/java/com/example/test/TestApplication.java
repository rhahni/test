package com.example.test;

import com.example.test.entities.Patient;
import com.example.test.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class TestApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;
    public static void main(String[] args) {

        SpringApplication.run(TestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for(int i=0;i<100;i++)
        {
            patientRepository.save(
                    new Patient(null,"oussama",new Date(), Math.random()>0.5?true:false,(int)(Math.random()*100)));
        }


        Page<Patient> patients=patientRepository.findAll(PageRequest.of(1,5));
        System.out.println("Total Pages : "+patients.getTotalPages());
        System.out.println("Total Element : "+patients.getTotalElements());
        System.out.println("Num Pages : "+patients.getNumber());
        List<Patient> content = patients.getContent();
        Page<Patient> byMalade = patientRepository.findByMalade(true, PageRequest.of(1,5));
        List<Patient> patientList=patientRepository.chercherPatients("%s%",50);

        byMalade.forEach(p->{
            System.out.println("===============================");
            System.out.println(p.getId());
            System.out.println(p.getNom());
            System.out.println(p.getScore());
            System.out.println(p.getDateNaissance());
            System.out.println(p.isMalade());
        });
        System.out.println("*************************");
        Patient patient=patientRepository.findById(1L).orElse(null);
        if (patient!=null)
        {
            System.out.println(patient.getNom());
            System.out.println(patient.isMalade());
        }
        patient.setScore(590);
        patientRepository.save(patient);
        patientRepository.deleteById(1L);
    }

}
