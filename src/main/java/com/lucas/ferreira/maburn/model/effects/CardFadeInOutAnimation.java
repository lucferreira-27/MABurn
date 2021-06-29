package com.lucas.ferreira.maburn.model.effects;

public class CardFadeInOutAnimation implements FadeInOutAnimation{

	private AnimationOpacityCard animationOpacityCard;
	
	public CardFadeInOutAnimation(AnimationOpacityCard animationOpacityCard) {
		this.animationOpacityCard = animationOpacityCard;
	}
	
	
	@Override
	public void fadeIn() {
		animationOpacityCard.fadeInCardBody(1, 0.5 / 100);
	}

	@Override
	public void fadeOut() {
		animationOpacityCard.fadeOutCardBody(1, 0.5 / 100);
	}
	
	
}
