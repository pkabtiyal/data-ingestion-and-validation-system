package app.data_ingestion.services.validationAndIngestion;

import java.sql.SQLException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.data_ingestion.config.ConfigReader;
import app.data_ingestion.helpers.QueryConstants;
import app.data_ingestion.helpers.QueryModificationHelper;

@Service
public class InsertRecordsState implements IState {

    final String FlAG_FOR_REPLACE = "1";


    /**
     * create table if not exist before ingestion
     * insert records with an auto increment id
     * @param ingestionService
     * @param id
     * @param file
     * @param delimiter
     * @param action
     * @return IState
     * @throws Exception
     */
    @Override
    public IState execute(IngestionService ingestionService, int id, MultipartFile file, String delimiter, String action)
            throws Exception {

        System.out.println("----inside InsertRecordsState-------");
        // insert data
        if (!ingestionService.getValidRows().isEmpty()) {
            createTable(ingestionService);

            String actionFlagFromConfig = ConfigReader.getInstance().getProperty(action);
            if (actionFlagFromConfig != null && actionFlagFromConfig.equals(FlAG_FOR_REPLACE)) {
                deleteRecordsFromDatabase(ingestionService);
            }

            insertRecordsToDatabase(ingestionService);
        }
        return null;
    }


    /**
     * create table if not exists
     * create an extra auto increment primary key id
     * @param ingestionService
     */
    public void createTable(IngestionService ingestionService) {
        String colDetail = QueryConstants.ID_AUTO_INCREMENT;

        for (String col : ingestionService.getMapColumnToDatatype().keySet()) {
            String dtype = ingestionService.getMapColumnToDatatype().get(col);
            colDetail += String.format("`%s` %s,", col, QueryModificationHelper.getDataType(dtype));
        }
        colDetail += QueryConstants.ID_PRIMARY_KEY;
        String query = String.format(QueryConstants.CREATE_TABLE_QUERY, ingestionService.getFileType().getFileTypeName(), colDetail);
        ingestionService.getQueryExecutor().execute(query);
    }


    /**
     * delete records from the table in case of replace
     * @param ingestionService
     */
    public void deleteRecordsFromDatabase(IngestionService ingestionService) {

        String query = String.format(QueryConstants.DELETE_QUERY, ingestionService.getFileType().getFileTypeName());
        ingestionService.getQueryExecutor().execute(query);
    }


    /**
     * insert rows to the database table
     * @param ingestionService
     * @throws SQLException
     */
    public void insertRecordsToDatabase(IngestionService ingestionService) throws SQLException {
        ingestionService.queryExecutor.insertRecords(ingestionService.getHeaders(),
                ingestionService.getFileType().getFileTypeName(),
                ingestionService.getValidRows(),
                ingestionService.getMapColumnToDatatype());
    }

}
