package com.example.demo.service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Driver;
import com.example.demo.model.DriverModel;
import com.example.demo.repository.DriverRepository;
import com.example.demo.request.DriverRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DriverService {

    private static List<DriverModel> drivers = new ArrayList<>();

    @Autowired
    private DriverRepository driverRepository;

    static {
        DriverModel d1 = new DriverModel("Max Verstappen", 33, "Netherlands");
        DriverModel d2 = new DriverModel("Lewis Hamilton", 44, "United Kingdom");

        drivers.add(d1);
        drivers.add(d2);
    }

    public Driver create(DriverRequest driverRequest) {
        Driver driver = new Driver();
        driver.setName(driverRequest.getName());
        driver.setCountry(driverRequest.getCountry());
        driver.setNum(driverRequest.getNumber());
        return driverRepository.save(driver);
    }

    public List<DriverModel> show() {
        return drivers;
    }

    public DriverModel show(Integer number) {
        for (DriverModel driver : drivers) {
            if (Objects.equals(driver.getNumber(), number)) {
                return driver;
            }
        }
        throw new NotFoundException();
    }

    public String setTeam(Integer number, String team) {
        String msg = "Piloto não encontrado";
        for (DriverModel driver : drivers) {
            if (Objects.equals(driver.getNumber(), number)) {
                if (Objects.equals(driver.getTeam(), null)) {
                    driver.setTeam(team);
                    msg = "Equipe " + team + " adicionada com sucesso ao piloto " + driver.getName();
                }
                else msg = "Piloto já possui equipe";
            }
        }
        return msg;
    }

}
