package com.mindex.challenge.service.impl;



import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String reportingStructureIdUrl;

    @Autowired
    private ReportingStructureService reportingStructureService;


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportingStructureIdUrl = "http://localhost:" + port + "/employee/{id}/reportingStructure";
    }

    @Test
    public void testCreateRead(){
        ReportingStructure testReportingStructure = new ReportingStructure(null,4);
        String employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";

        // Create checks
        ReportingStructure createdReportingStructure = restTemplate.getForEntity(reportingStructureIdUrl, ReportingStructure.class,employeeId).getBody();

        assertNotNull(createdReportingStructure.getEmployee());
        assertReportingStructureEquivalence(testReportingStructure, createdReportingStructure);
    }


     private static void assertReportingStructureEquivalence(ReportingStructure expected, ReportingStructure actual) {
        assertEquals(expected.getNumberOfReports(), actual.getNumberOfReports());

    }
}
