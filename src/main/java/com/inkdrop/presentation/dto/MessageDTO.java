package com.inkdrop.presentation.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDTO {

  private String userAccessToken;
  private Integer roomUid;
  private String content;
}
