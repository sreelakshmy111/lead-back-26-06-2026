package com.project.Permission.of.lead.entity.code;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "m_code")
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class Mcode {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_code_generator")
    @SequenceGenerator(
            name = "m_code_generator",
            sequenceName = "m_code_id_seq", // Name of the PostgreSQL sequence
            initialValue = 33,             // Starts the sequence at 33
            allocationSize = 1             // Corresponds to INCREMENT 1
    )
    private Long id;


    @Column(name = "code_name",length = 100)
    private String codeName;

    @Column(name = "is_system_default",nullable = false)
    private boolean systemDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "parent_id", // <-- THIS IS THE FOREIGN KEY COLUMN IN THE M_CODE TABLE
            foreignKey = @ForeignKey(name = "fk_parent_m_code")
    )
    private Mcode parent;

    @OneToMany(mappedBy = "code", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("code") // Prevents infinite loop when serializing a master
    @OrderBy("orderPosition ASC")
    private List<McodeValue> codeValues; // <-- NEW: Collection of children Mcode objects


    // Add this constructor
    public Mcode(Long id, String codeName, boolean systemDefault, Mcode parent) {
        this.id = id;
        this.codeName = codeName;
        this.systemDefault = systemDefault;
        this.parent = parent;
    }


}
