package com.protectimus.api.sdk.enums;

public enum TokenType {

    GOOGLE_AUTHENTICATOR(Type.SOFTWARE), PROTECTIMUS(Type.HARDWARE), SAFENET_ETOKEN_PASS(
            Type.HARDWARE), SMS(Type.SOFTWARE), MAIL(Type.SOFTWARE), PROTECTIMUS_ULTRA(
            Type.HARDWARE), PROTECTIMUS_SMART(Type.SOFTWARE), YUBICO_OATH_MODE(
            Type.HARDWARE), UNIFY_OATH_TOKEN(Type.SOFTWARE), PROTECTIMUS_SLIM(Type.HARDWARE);

	public enum Type {
		SOFTWARE, HARDWARE
	}

	private Type type;

	private TokenType(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

}