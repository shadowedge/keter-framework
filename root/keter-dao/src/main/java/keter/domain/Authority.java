package keter.domain;

/**
 * Authority given to user. DIfferent authorities allows users to execute
 * different actions. User should have at least one authority to be able to
 * anything.
 * 
 * User with no authorities is considered as disabled.
 * 
 * @author Maciej Hamiga
 * 
 */
public enum Authority {
	ADMIN,
	USER
}
