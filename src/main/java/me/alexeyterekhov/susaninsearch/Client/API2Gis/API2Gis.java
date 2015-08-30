package me.alexeyterekhov.susaninsearch.Client.API2Gis;

import me.alexeyterekhov.susaninsearch.Data.Firm;

import java.util.List;

public interface API2Gis {
    List<Firm> searchPopularFirms(String what, String where);
    String getFirmDetails(String firmId, String firmHash);
}