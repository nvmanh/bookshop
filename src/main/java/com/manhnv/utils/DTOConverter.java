package com.manhnv.utils;

import org.modelmapper.ModelMapper;

import com.manhnv.entity.Author;
import com.manhnv.entity.Book;
import com.manhnv.entity.User;
import com.manhnv.entity.Vote;
import com.manhnv.model.dto.AuthorDTO;
import com.manhnv.model.dto.UserDTO;
import com.manhnv.model.dto.VoteDTO;
import com.manhnv.model.request.BookCreateRequest;

public class DTOConverter {

	public static AuthorDTO convertAuthorDTO(Author author, ModelMapper modelMapper) {
		return modelMapper.map(author, AuthorDTO.class);
	}

	public static UserDTO convertUserDTO(User user, ModelMapper modelMapper) {
		return modelMapper.map(user, UserDTO.class);
	}

	public static VoteDTO convertVoteDTO(Vote vote, ModelMapper modelMapper) {
		return modelMapper.map(vote, VoteDTO.class);
	}
	
	public static Book convertBook(BookCreateRequest bookCreateRequest, ModelMapper modelMapper) {
		return modelMapper.map(bookCreateRequest, Book.class);
	}
}
