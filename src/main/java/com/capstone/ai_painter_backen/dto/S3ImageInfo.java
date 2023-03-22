package com.capstone.ai_painter_backen.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class S3ImageInfo {
    Long S3ImageInfoId;
    String fileName;
    String fileURI;
}