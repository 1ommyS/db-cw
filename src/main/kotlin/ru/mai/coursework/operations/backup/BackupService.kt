package ru.mai.coursework.operations.backup

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.mai.coursework.entity.Backup
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.infrastructure.async.TaskPublisher
import ru.mai.coursework.infrastructure.repository.backup.BackupRepository
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate

@Service
@Log
data class BackupService(val taskPublisher: TaskPublisher, val backupRepository: BackupRepository) {
    private val logger = LoggerFactory.getLogger(BackupService::class.java.name)

    @Value("\${backup.directory:/path/to/backup/directory}")
    val backupDir: String? = null

    @Value("\${spring.datasource.username}")
    val dbUser: String? = null

    @Value("\${backup.dbName}")
    val dbName: String? = null

    suspend fun createBackup(): String {
        logger.info(backupDir)
        val timestamp: String = LocalDate.now().toString()
        val backupFileName = "backup_$timestamp.dump"
        val backupPath = Paths.get(backupDir, backupFileName).toString()

        withContext(Dispatchers.IO) {
            Files.createDirectories(Paths.get(backupDir))
        }

        createDump()
        copyBackupFromContainer(backupPath)
        backupRepository.saveBackup(backupFileName)

        return backupFileName
    }

    suspend fun restoreBackup(fileName: String) {
        val backupPath = Paths.get(backupDir, fileName).toString()

        copyBackupToContainer(backupPath)
        applyDump()
    }

    @Log
    private suspend fun createDump() {
        val command =
            "docker exec $POSTGRES_CONTAINER_NAME pg_dump -U $dbUser -d $dbName -F c -b -v -f /tmp/backup.dump"
        taskPublisher.submit(command, TIMEOUT_MILLIS)
    }

    private suspend fun copyBackupFromContainer(backupPath: String) {
        val copyCommand = "docker cp $POSTGRES_CONTAINER_NAME:/tmp/backup.dump $backupPath"
        taskPublisher.submit(copyCommand, TIMEOUT_MILLIS)
    }

    private suspend fun copyBackupToContainer(backupPath: String) {
        val copyCommand = "docker cp $backupPath $POSTGRES_CONTAINER_NAME:/tmp/backup.dump"
        taskPublisher.submit(copyCommand, TIMEOUT_MILLIS)
    }

    private suspend fun applyDump() {
        val restoreCommand =
            "docker exec -t $POSTGRES_CONTAINER_NAME pg_restore -U $dbUser -d $dbName -v /tmp/backup.dump"
        taskPublisher.submit(restoreCommand, TIMEOUT_MILLIS)
    }

    fun findAll(): List<Backup> {
        return backupRepository.findAll()
    }

    companion object {
        private const val TIMEOUT_MILLIS = (60 * 1000).toLong()
        private const val POSTGRES_CONTAINER_NAME = "coursework-postgres-1"
    }
}