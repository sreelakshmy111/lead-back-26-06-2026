package com.project.Permission.of.lead.entity.code;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "m_code_value")

public class McodeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isActive;

    private String codeDescription;

    private String codeValue;

    private boolean isMandatory;

    private int orderPosition;

    @Column(name = "store_value",length = 100)
    private String storeValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "code_id", nullable = false, foreignKey = @ForeignKey(name = "fk_code_id_m_code_id"))
    private Mcode code; // Parent code

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_parent_m_code_value"))
    private McodeValue parent; // Optional parent value for nesting


    public McodeValue(McodeValue parent, Mcode code, String storeValue, int orderPosition, boolean isMandatory, String codeValue, String codeDescription, boolean isActive, Long id) {
        this.parent = parent;
        this.code = code;
        this.storeValue = storeValue;
        this.orderPosition = orderPosition;
        this.isMandatory = isMandatory;
        this.codeValue = codeValue;
        this.codeDescription = codeDescription;
        this.isActive = isActive;
        this.id = id;
    }
}
