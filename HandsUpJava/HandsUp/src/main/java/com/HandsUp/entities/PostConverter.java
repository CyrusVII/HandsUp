package com.HandsUp.entities;

import java.util.Date;

public class PostConverter {

    public static PostDto toDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTesto(post.getTesto());
        dto.setDataPubblicazione(post.getDataPubblicazione().toString());
        dto.setMiPiace(post.getMiPiace());
        dto.setNonMiPiace(post.getNonMiPiace());
        dto.setUtenteUsername(post.getUtente().getUsername());
        dto.setAziendaUsername(post.getAzienda().getUsername());
        return dto;
    }

    public static Post toEntity(PostDto dto) {
        Post post = new Post();
        post.setId(dto.getId());
        post.setTesto(dto.getTesto());
        post.setDataPubblicazione(new Date(dto.getDataPubblicazione()));  // Assicurati di gestire correttamente la conversione della data
        post.setMiPiace(dto.getMiPiace());
        post.setNonMiPiace(dto.getNonMiPiace());
        // Nota: L'utente e l'azienda devono essere caricati separatamente dal database e settati
        return post;
    }
}
