package com.example.myproject.Service;

import com.example.myproject.DTO.BranchwiseDTO;
import com.example.myproject.DTO.ClientsDTO;
import com.example.myproject.Repository.ClientsRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

@Service
public class ClientService {
    @Autowired
    private ClientsRepo cr;
    private static int totalCount;
    public static String allxmldata="";
    public static String  brncount ="";

    private static final ExecutorService executor = Executors.newCachedThreadPool();


    private int getTotalClients(String clientsName) {
        return cr.countByClientsName(clientsName);
    }

    public Map<String, String> getClientsData (Map<String, String> inputObj) {
        String nameXml = inputObj.get("NAME_XML");
       // String keyCode = inputObj.get("KEY_CODE");
        long reportSl = Long.parseLong(inputObj.get("REPORT_SL"));
        long startSl = Long.parseLong(inputObj.get("START_SL"));
        long endSl = Long.parseLong(inputObj.get("END_SL"));
        Instant start = Instant.now();
        Map<String, String> Resultobj = new HashMap<>();
        Resultobj.put("ResultKey", "RowNotPresent");
        if (reportSl == 1) {
            totalCount = getTotalClients(nameXml);
        }
        int nextRecAvl = 0;



        if(endSl>totalCount) {
            endSl = totalCount;
        }
        ArrayList<Long> clientCodes = cr.getClientsCode(nameXml, endSl, startSl);
        Long extime = logExecutionTime(start);
        Resultobj.put("START_SL", String.valueOf(startSl));
        if (clientCodes.isEmpty()) {
            System.out.println("No CLIENTS_CODE found.");
            return Resultobj;
        }
        Resultobj.put("ResultKey", "RowPresent");
        Resultobj.put("EXTIME", String.valueOf(extime));
        Resultobj.put("TOTALCNT", String.valueOf(totalCount));
        ArrayList<ClientsDTO> clientsData = cr.getClientsData(clientCodes);

        String clxml = getXMLData(clientsData, startSl,endSl);
        Resultobj.put("XML_STR", clxml);
        if(endSl<totalCount) {
            nextRecAvl =1;
        }
        Resultobj.put("END_SL", String.valueOf(endSl));
        Resultobj.put("NEXT_REC_AVL", String.valueOf(nextRecAvl));
        if (reportSl == 1) {
            executor.submit(() -> fetchalldata(nameXml));
            executor.submit(() -> fetchbrnCount(nameXml));
        }

        return Resultobj;
    }

    private String getXMLData(ArrayList<ClientsDTO> clientsData, long start, long end) {
        StringBuffer sb = new StringBuffer("<Data>");
        long serialNumber = start;

        for (int i = 0; i < clientsData.size(); i++) {
            if (serialNumber >= end) break;

            ClientsDTO client = clientsData.get(i);
            sb.append("<Record>")
                    .append("<Serial>").append(serialNumber).append("</Serial>")
                    .append("<Cell>").append(client.getClientsCode()).append("</Cell>")
                    .append("<Cell>").append(client.getClientsName()).append("</Cell>")
                    .append("<Cell>").append(client.getFatherName()).append("</Cell>")
                    .append("<Cell>").append(client.getAccountNumber()).append("</Cell>")
                    .append("<Cell>").append(client.getPhoneNumber()).append("</Cell>")
                    .append("<Cell>").append(client.getHomeBranchCode()).append("</Cell>")
                    .append("<Cell>").append(client.getTypeFlag()).append("</Cell>")
                    .append("<Cell>").append(client.getPanNumber()).append("</Cell>")
                    .append("<Cell>").append(client.getAddress1()).append(client.getAddress2()).append(client.getAddress3())
                    .append(client.getAddress4()).append(client.getAddress5()).append("</Cell>")
                    .append("</Record>");
            serialNumber++;
        }

        sb.append("</Data>");
        return sb.toString();
    }

    public void fetchalldata(String namexml) {
        ArrayList<ClientsDTO> allClients = cr.getAllClientsData(namexml);
        allxmldata = getXMLData(allClients, 1, totalCount);
    }

    public void fetchbrnCount(String namexml) {

            StringBuffer cntxml = new StringBuffer("<records>");

            ArrayList<BranchwiseDTO> brnwsdata = cr.getBranchwiseData(namexml);
            for (BranchwiseDTO br : brnwsdata) {
                cntxml.append("<record>").append("<label>").append(br.getHomeBranchCode()).append("</label>").append("<data>").append(br.getCount()).append("</data>").append("</record>");
            }

            cntxml.append("</records>");
            brncount = cntxml.toString();
            System.out.println("for all branchwise data : " + cntxml);

    }



    private Long logExecutionTime(Instant start) {
        Instant end = Instant.now();
        return  Duration.between(start, end).toMillis();
    }


}


