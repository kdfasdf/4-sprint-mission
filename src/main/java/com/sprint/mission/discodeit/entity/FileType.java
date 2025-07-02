package com.sprint.mission.discodeit.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FileType {
    ZIP("FILE-000", "application/zip"),

    JPG("FILE-100", "image/jpeg"),
    JPEG("FILE-101", "image/jpeg"),
    PNG("FILE-102", "image/png"),
    GIF("FILE-103", "image/gif"),

    MP3("FILE-200", "audio/mpeg"),
    WAV("FILE-201", "audio/wav"),
    OGG("FILE-202", "audio/ogg"),
    FLAC("FILE-203", "audio/flac"),

    MP4("FILE-300", "video/mp4"),
    AVI("FILE-301", "video/x-msvideo"),
    MKV("FILE-302", "video/x-matroska"),
    WMV("FILE-303", "video/x-ms-wmv"),

    DOC("FILE-400", "application/msword"),
    DOCX("FILE-401", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    PDF("FILE-402", "application/pdf"),
    XLS("FILE-403", "application/vnd.ms-excel"),
    XLSX("FILE-404", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    TXT("FILE-405", "text/plain"),
    RTF("FILE-406", "application/rtf"),
    CSV("FILE-407", "text/csv"),
    HTML("FILE-408", "text/html"),
    XML("FILE-409", "application/xml"),
    JSON("FILE-410", "application/json"),
    HWP("FILE-411", "application/x-hwp"),
    PPTX("FILE-412", "application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    PPT("FILE-413", "application/vnd.ms-powerpoint"),
    ODT("FILE-414", "application/vnd.oasis.opendocument.text"),
    ODS("FILE-415", "application/vnd.oasis.opendocument.spreadsheet"),
    ODP("FILE-416", "application/vnd.oasis.opendocument.presentation");


    private final String code;
    private final String extension;

    @JsonCreator
    public static FileType fromJson(@JsonProperty("code") String code, @JsonProperty("extension") String extension) {
        return Arrays.stream(FileType.values())
                .filter(fileType -> fileType.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("FileType not found. code=" + code));
    }

    public static FileType getFileTypeByCode(String code) {
        return Arrays.stream(FileType.values())
                .filter(fileType -> fileType.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("FileType not found."));
    }

    public static FileType getFileTypeByExtension(String extension) {
        return Arrays.stream(FileType.values())
                .filter(fileType -> fileType.getExtension().equals(extension))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("FileType not found."));
    }

    public String getExtensionByCode(String code) {
        return Arrays.stream(FileType.values())
                .filter(fileType -> fileType.getCode().equals(code))
                .findFirst()
                .map(FileType::getExtension)
                .orElseThrow(() -> new IllegalArgumentException("FileType not found."));
    }

}
