package com.HandsUp.entities;

import java.util.Base64;

public class CommentoConverter {

	public static CommentoDto toDto(Commento commento) {
	    CommentoDto dto = new CommentoDto();
	    dto.setId(commento.getId());
	    dto.setTesto(commento.getTesto());
	    dto.setMiPiace(commento.getMiPiace());
	    dto.setImmagine(commento.getImmagine());
	    dto.setPostId(commento.getPost().getId());
	    dto.setUtenteUsername(commento.getUtente() != null ? commento.getUtente().getUsername() : null);
	    dto.setAziendaUsername(commento.getAzienda() != null ? commento.getAzienda().getUsername() : null);

	    // Aggiungi l'immagine dell'utente se disponibile
	    if (commento.getUtente() != null && commento.getUtente().getImmagine() != null) {
	        String utenteImageBase64 = Base64.getEncoder().encodeToString(commento.getUtente().getImmagine());
	        dto.setUtenteImmagine("data:image/jpeg;base64," + utenteImageBase64);
	    }
	    
	    if (commento.getAzienda() != null && commento.getAzienda().getImmagine() != null) {
            String aziendaImageBase64 = Base64.getEncoder().encodeToString(commento.getAzienda().getImmagine());
            dto.setAziendaImmagine("data:image/jpeg;base64," + aziendaImageBase64);
        }

	    
	    return dto;
	}

}
