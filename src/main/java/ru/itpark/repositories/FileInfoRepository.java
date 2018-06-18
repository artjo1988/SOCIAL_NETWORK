package ru.itpark.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.itpark.models.FileInfo;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
    FileInfo findOneByStorageName(String storageFileName);

//    @Modifying
//    @Query(nativeQuery = true, value = "UPDATE avatar_sn SET original_name = ?1, storage_name =?2, " +
//            "size = ?3, type = ?4, url = ?5 WHERE id = ?6")
//    void updateFileInfoById(String originalName, String storageName, Long size, String type,
//                            String url, Long id);
}
