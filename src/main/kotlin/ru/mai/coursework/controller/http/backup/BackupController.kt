package ru.mai.coursework.controller.http.backup

import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import ru.mai.coursework.entity.Backup
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.operations.backup.BackupService
import java.nio.file.Files
import java.nio.file.Paths

@RestController
@Log
@RequestMapping("/backup")
data class BackupController(val backupService: BackupService) {

    @PostMapping
    suspend fun createBackup(): Any {
        try {
            val backupFileName: String = backupService.createBackup()
            return backupFileName
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body<String>("Ошибка при создании бэкапа: " + e.message)
        }
    }

    @PostMapping("/restore")
    suspend fun restoreBackup(@RequestParam("fileName") fileName: String): ResponseEntity<String> {
        try {
            if (!StringUtils.hasText(fileName) || fileName.contains("..") || !fileName.endsWith(".dump")) {
                return ResponseEntity.badRequest().body<String>("Некорректное имя файла.")
            }

            val backupPath = Paths.get(backupService.backupDir, fileName).toString()
            if (!Files.exists(Paths.get(backupPath))) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body<String>("Файл бэкапа не найден.")
            }

            backupService.restoreBackup(fileName)
            return ResponseEntity.ok("База данных успешно восстановлена из бэкапа: $fileName")
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ошибка при восстановлении бэкапа: " + e.message)
        }
    }

    @GetMapping("/download")
    fun downloadBackup(@RequestParam("fileName") fileName: String): ResponseEntity<Resource> {
        try {
            if (!StringUtils.hasText(fileName) || fileName.contains("..") || !fileName.endsWith(".dump")) {
                return ResponseEntity.badRequest().body<Resource>(null)
            }

            val backupPath = Paths.get(backupService.backupDir, fileName).toString()
            if (!Files.exists(Paths.get(backupPath))) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body<Resource>(null)
            }

            val resource: FileSystemResource = FileSystemResource(backupPath)

            val headers = HttpHeaders()
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"$fileName\"")
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)

            return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .body<Resource>(resource)
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body<Resource>(null)
        }
    }


    @GetMapping("/list")
    fun findAll(): List<Backup> {
        return backupService.findAll()
    }
}