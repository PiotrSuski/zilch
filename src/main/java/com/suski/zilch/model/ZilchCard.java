package com.suski.zilch.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CARD")
public class ZilchCard implements Serializable {

	private static final long serialVersionUID = 712344981384024475L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Valid
	@NotNull
	@Embedded
	protected User user;

	@NotNull
	@Column(name = "NUMBER", nullable = false)
	protected String cardnumber;

	public ZilchCard() {
	}

	public ZilchCard(@NotNull User user, @NotNull String cardnumber) {
		this.user = user;
		this.cardnumber = cardnumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

}
