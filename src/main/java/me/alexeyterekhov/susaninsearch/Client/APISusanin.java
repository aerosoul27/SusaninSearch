package me.alexeyterekhov.susaninsearch.Client;

import me.alexeyterekhov.susaninsearch.Data.Firm;

public interface APISusanin {
    Firm findMostPopular(String what, String where);
}
