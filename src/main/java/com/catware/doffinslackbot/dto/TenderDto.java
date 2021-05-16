package com.catware.doffinslackbot.dto;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;

import java.util.Objects;

@Document(collectionName = "tenders")
public class TenderDto {
    private String name;
    private String url;
    private String publishedBy;
    private String publishedDate;
    private String expiresDate;
    @DocumentId
    private String doffinReference;

    public TenderDto() {
    }

    public TenderDto(String name, String url, String publishedBy, String publishedDate, String expiresDate, String doffinReference) {
        this.name = name;
        this.url = url;
        this.publishedBy = publishedBy;
        this.publishedDate = publishedDate;
        this.expiresDate = expiresDate;
        this.doffinReference = doffinReference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublishedBy() {
        return publishedBy;
    }

    public void setPublishedBy(String publishedBy) {
        this.publishedBy = publishedBy;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(String expiresDate) {
        this.expiresDate = expiresDate;
    }

    public String getDoffinReference() {
        return doffinReference;
    }

    public void setDoffinReference(String doffinReference) {
        this.doffinReference = doffinReference;
    }

    @Override
    public String toString() {
        return "TenderDto{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", publishedBy='" + publishedBy + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                ", expiresDate='" + expiresDate + '\'' +
                ", doffinReference='" + doffinReference + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TenderDto tenderDto = (TenderDto) o;
        return doffinReference.equals(tenderDto.doffinReference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doffinReference);
    }
}
