package com.rdele.drone;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdele.drone.controller.DroneController;
import com.rdele.drone.dto.DroneDTO;
import com.rdele.drone.dto.MedicationDTO;
import com.rdele.drone.enums.DroneState;
import com.rdele.drone.service.DroneService;

@WebMvcTest(controllers = DroneController.class)
@Import(DroneControllerTest.TestConfig.class)
public class DroneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DroneService droneService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public DroneService droneService() {
            return Mockito.mock(DroneService.class);
        }
    }

    @Test
    void testRegisterDrone() throws Exception {
        DroneDTO droneDTO = new DroneDTO("DRONE-001", "LIGHTWEIGHT", 75, DroneState.IDLE);
        Mockito.when(droneService.registerDrone(any(DroneDTO.class))).thenReturn(droneDTO);

        mockMvc.perform(post("/api/drones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(droneDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.serialNumber").value("DRONE-001"));
    }

    @Test
    void testLoadDrone() throws Exception {
        String serialNumber = "DRONE-001";
        List<MedicationDTO> meds = List.of(
            new MedicationDTO("Med1", 50, "CODE1", "img1"),
            new MedicationDTO("Med2", 30, "CODE2", "img2")
        );

        Mockito.when(droneService.loadDrone(eq(serialNumber), any())).thenReturn(meds);

        mockMvc.perform(post("/api/drones/{serialNumber}/load", serialNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(meds)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Med1"))
            .andExpect(jsonPath("$[1].weight").value(30));
    }

    @Test
    void testDeliverMedications() throws Exception {
        String serialNumber = "DRONE-001";
        Mockito.when(droneService.deliverMedications(serialNumber)).thenReturn("Delivery started");

        mockMvc.perform(post("/api/drones/{serialNumber}/deliver", serialNumber))
            .andExpect(status().isOk())
            .andExpect(content().string("Delivery started"));
    }

    @Test
    void testGetDroneDetail() throws Exception {
        String serialNumber = "DRONE-001";
        DroneDTO dto = new DroneDTO(serialNumber, "LIGHTWEIGHT", 80, DroneState.IDLE);
        Mockito.when(droneService.getDroneDetail(serialNumber)).thenReturn(dto);

        mockMvc.perform(get("/api/drones/{serialNumber}", serialNumber))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.serialNumber").value(serialNumber))
            .andExpect(jsonPath("$.model").value("LIGHTWEIGHT"));
    }

    @Test
    void testGetMedications() throws Exception {
        String serialNumber = "DRONE-001";
        List<MedicationDTO> meds = List.of(
            new MedicationDTO("Med1", 50, "CODE1", "img1")
        );
        Mockito.when(droneService.getLoadedMedications(serialNumber)).thenReturn(meds);

        mockMvc.perform(get("/api/drones/{serialNumber}/medications", serialNumber))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Med1"));
    }

    @Test
    void testGetAvailableDrones() throws Exception {
        List<DroneDTO> drones = List.of(
            new DroneDTO("DRONE-001", "LIGHTWEIGHT", 80, DroneState.IDLE),
            new DroneDTO("DRONE-002", "MIDDLEWEIGHT", 90, DroneState.IDLE)
        );
        Mockito.when(droneService.getAvailableDrones()).thenReturn(drones);

        mockMvc.perform(get("/api/drones/available"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].serialNumber").value("DRONE-001"))
            .andExpect(jsonPath("$[1].serialNumber").value("DRONE-002"));
    }

    @Test
    void testGetBatteryLevel() throws Exception {
        String serialNumber = "DRONE-001";
        Mockito.when(droneService.getBatteryLevel(serialNumber)).thenReturn(85);

        mockMvc.perform(get("/api/drones/{serialNumber}/battery", serialNumber))
            .andExpect(status().isOk())
            .andExpect(content().string("85"));
    }
}