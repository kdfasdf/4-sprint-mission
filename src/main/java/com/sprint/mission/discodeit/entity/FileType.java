package com.sprint.mission.discodeit.entity;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileType {
    ZIP("FILE-000", "ZIP"),

    JPG("FILE-100", "JPG"),
    JPEG("FILE-101", "JPEG"),
    PNG("FILE-102", "PNG"),
    GIF("FILE-103", "GIF"),

    MP3("FILE-200", "MP3"),
    WAV("FILE-201", "WAV"),
    OGG("FILE-202", "OGG"),
    FLAC("FILE-203", "FLAC"),

    MP4("FILE-300", "MP4"),
    AVI("FILE-301", "AVI"),
    MKV("FILE-302", "MKV"),
    WMV("FILE-303", "WMV"),

    DOC("FILE-400", "DOC"),
    DOCX("FILE-401", "DOCX"),
    PDF("FILE-402", "PDF"),
    XLS("FILE-403", "XLS"),
    XLSX("FILE-404", "XLSX"),
    TXT("FILE-405", "TXT"),
    RTF("FILE-406", "RTF"),
    CSV("FILE-407", "CSV"),
    HTML("FILE-408", "HTML"),
    XML("FILE-409", "XML"),
    JSON("FILE-410", "JSON"),
    HWP("FILE-411", "HWP"),
    PPTX("FILE-412", "PPTX"),
    PPT("FILE-413", "PPT"),
    ODT("FILE-414", "ODT"),
    ODS("FILE-415", "ODS"),
    ODP("FILE-416", "ODP");


    private final String code;
    private final String extension;

    public static FileType getFileTypeByCode(String code) {
        return Arrays.stream(FileType.values())
                .filter(fileType -> fileType.getCode().equals(code))
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
