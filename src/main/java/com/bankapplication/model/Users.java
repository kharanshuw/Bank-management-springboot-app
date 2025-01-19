package com.bankapplication.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@Table(name = "users")
@Entity
public class Users {

    private static final Logger logger = LoggerFactory.getLogger(Users.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Password", nullable = false)
    @NotEmpty(message = "Password should not be empty")
    @Pattern(regexp = "\\{noop\\}(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,128}$", message = "Password must meet requirements (8-128 chars, uppercase, lowercase, digit, special char)")
    private String password;

    @Column(name = "email", unique = true)
    @NotEmpty(message = "Email should not be empty")
    @NotNull(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email should be valid")
    private String email;

    @Column(name = "enable")
    private boolean isActive = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_details_id", referencedColumnName = "id")
    private UserDetails userDetails;

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> role;

    // parametarized constructor
    public Users(int id,
                 @Size(min = 8, max = 128, message = "password length must be between 0 to 128") @NotEmpty(message = "password should not be empty") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,128}$", message = "Password must meet requirements (8-128 chars, uppercase, lowercase, digit, special char)") String password,
                 @NotEmpty(message = "email should not be empty") @NotNull(message = "email should not be empty") String email,
                 boolean isActive, UserDetails userDetails, Set<Role> role) {
        super();
        this.id = id;
        this.password = password;
        this.email = email;
        this.isActive = isActive;
        this.userDetails = userDetails;
        this.role = role;
    }

    public Users() {
        super();
        // TODO Auto-generated constructor stub
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    // user will be stored in set so no redundent roles will be assined
    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }

    // this method is used to add new role for user
    public void addrole(Role roleobj) {
        // if paramerter roleobj is not null then only this if will get executed
        // this roleobj show role object to add in user
        if (roleobj != null) {

            // if this.role is null then initialize with hashset
            if (this.role == null) {
                this.role = new HashSet<>();
            }

            // add role object in this.role
            this.role.add(roleobj);
        }
    }

    /**
     * Removes a role from the user's set of roles.
     *
     * @param roleobj the role to be removed
     */
    public void removeRole(Role roleobj) {
        // Check if the role object passed as parameter is null
        if (roleobj == null) {
            logger.error("role object passes as parameter is null");
            return;
        }

        // Check if the role set is not null
        if (this.role != null) {
            // Attempt to remove the role from the set
            if (this.role.remove(roleobj)) {
                logger.info("role removed successfully" + roleobj.toString());

            } else {
                // Log a warning if the role was not found in the set
                logger.warn("role not found to remove in user class removerole method " + roleobj.toString());
            }
        } else {
            // Log an error if the role set is null
            logger.error("role set is null cannot remove role");
        }
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                ", userDetails=" + userDetails +
                ", role=" + role +
                '}';
    }
}
