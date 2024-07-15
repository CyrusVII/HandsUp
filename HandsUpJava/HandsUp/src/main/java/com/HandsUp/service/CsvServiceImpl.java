package com.HandsUp.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HandsUp.entities.DataSetAziende;
import com.HandsUp.repository.DataSetAziendeRepository;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CsvServiceImpl implements CsvService {

    private static final Logger logger = LoggerFactory.getLogger(CsvServiceImpl.class);

    @Autowired
    private DataSetAziendeRepository repo;

    private static final String CSV_FILE_PATH = "src/main/resources/static/asset/csv/DatiAziende.csv";

    @Override
    @PostConstruct
    public void readCsvAndSaveData() {
        logger.info("Inizio inserimento dati");

        if (repo.count() > 0) {
            logger.info("Il repository non è vuoto, nessun dato verrà inserito");
            return;
        }

        try {
            List<String> lista = readCsv(CSV_FILE_PATH);
            List<DataSetAziende> dati = new ArrayList<>();
            for (String s : lista) {
                String[] supp = s.split(";");
                if (supp.length < 7) {
                    logger.warn("Riga malformata: " + s);
                    continue;
                }
                try {
                    // Creazione dell'oggetto DataSetAziende
                    DataSetAziende azienda = new DataSetAziende(
                            Long.parseLong(supp[5]), 
                            supp[0], 
                            supp[1], 
                            supp[2], 
                            supp[3], 
                            supp[4], 
                            supp[6]
                    );
                    dati.add(azienda);
                    logger.debug("Record aggiunto: " + azienda);
                } catch (NumberFormatException e) {
                    logger.error("Errore nel parsing del numero: " + s, e);
                } catch (Exception e) {
                    logger.error("Errore sconosciuto durante la creazione dell'oggetto DataSetAziende: " + s, e);
                }
            }

            // Salvataggio dei dati nel repository
            try {
                repo.saveAll(dati);
                logger.info("Dati inseriti correttamente nel database, totale: " + dati.size());
            } catch (Exception e) {
                logger.error("Errore durante il salvataggio dei dati nel database", e);
            }

        } catch (IOException e) {
            logger.error("Errore durante la lettura del file CSV", e);
        }
    }

    private List<String> readCsv(String filePath) throws IOException {
        List<String> lista = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String riga = reader.readLine(); // Legge l'intestazione
            while ((riga = reader.readLine()) != null) {
                lista.add(riga);
            }
        }
        return lista;
    }
}
