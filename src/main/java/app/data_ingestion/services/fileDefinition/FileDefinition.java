package app.data_ingestion.services.fileDefinition;

import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data_ingestion.dataLayer.dao.IFileTypeDao;
import app.data_ingestion.dataLayer.models.FileType;
import app.data_ingestion.services.userAuthAndRegister.UserServiceStatus;

@Service
public class FileDefinition implements IFileDefinitionService {

    @Autowired
    IFileTypeDao fileTypeDao;

    /**
     * add file_definition 
     * @param fileDef
     * @return
     */
    @Override
    public UserServiceStatus fileDefinition(FileType fileDef) {
        try {
            if (fileTypeDao.addFileDefinition(fileDef) > 0) {
                return UserServiceStatus.SUCCESS;
            } else {
                return UserServiceStatus.FAILURE;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UserServiceStatus.FAILURE;
    }

    @Override
    public FileType getFileDefinitionById(int file_definition_id) throws JsonProcessingException {
        FileType fileType = new FileType();
        try {
            fileType = fileTypeDao.getFileTypeById(file_definition_id);
            return fileType;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fileType;
    }

    /**
     * delete file_definition
     * @param fileDefinitionId
     * @return
     * @throws SQLException
     * @throws JsonProcessingException
     */
    @Override
    public boolean deleteFileDefinition(int fileDefinitionId) throws SQLException, JsonProcessingException {
        boolean status = fileTypeDao.deleteFileDefinition(fileDefinitionId);
        if (status) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * update file_definition
     * @param fileType
     * @return UserServiceStatus
     */
    @Override
    public UserServiceStatus updateFileDefinition(FileType fileType) {
        try {
            if (fileTypeDao.updateFileDefinition(fileType) > 0) {
                return UserServiceStatus.SUCCESS;
            } else {
                return UserServiceStatus.FAILURE;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UserServiceStatus.FAILURE;
    }

}
