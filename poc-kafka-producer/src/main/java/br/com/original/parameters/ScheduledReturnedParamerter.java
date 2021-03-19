package br.com.original.parameters;

import java.io.Serializable;
import java.math.BigInteger;

public class ScheduledReturnedParamerter implements Serializable {

    private BigInteger nuumIidtfdLote;
    private String nomTopcoRetorLote;
    private String uuidTrnsfLote;
    private BigInteger numIdtfdLote;
    private BigInteger codIrregTrnsf;
    private BigInteger codSglSist;
    private String hotAtulzTrnsf;
    private String codSprod;
    private BigInteger numReftPgto;
    private BigInteger codTrans;
    private BigInteger codMotvo;
    private BigInteger referenceNumber;
    private String paymentConcept;
    private String statusDate;
    private String comements;
    private String stampDateTime;

    public BigInteger getNuumIidtfdLote() {
        return nuumIidtfdLote;
    }

    public void setNuumIidtfdLote(BigInteger nuumIidtfdLote) {
        this.nuumIidtfdLote = nuumIidtfdLote;
    }

    public String getNomTopcoRetorLote() {
        return nomTopcoRetorLote;
    }

    public void setNomTopcoRetorLote(String nomTopcoRetorLote) {
        this.nomTopcoRetorLote = nomTopcoRetorLote;
    }

    public String getUuidTrnsfLote() {
        return uuidTrnsfLote;
    }

    public void setUuidTrnsfLote(String uuidTrnsfLote) {
        this.uuidTrnsfLote = uuidTrnsfLote;
    }

    public BigInteger getNumIdtfdLote() {
        return numIdtfdLote;
    }

    public void setNumIdtfdLote(BigInteger numIdtfdLote) {
        this.numIdtfdLote = numIdtfdLote;
    }

    public BigInteger getCodIrregTrnsf() {
        return codIrregTrnsf;
    }

    public void setCodIrregTrnsf(BigInteger codIrregTrnsf) {
        this.codIrregTrnsf = codIrregTrnsf;
    }

    public BigInteger getCodSglSist() {
        return codSglSist;
    }

    public void setCodSglSist(BigInteger codSglSist) {
        this.codSglSist = codSglSist;
    }

    public String getHotAtulzTrnsf() {
        return hotAtulzTrnsf;
    }

    public void setHotAtulzTrnsf(String hotAtulzTrnsf) {
        this.hotAtulzTrnsf = hotAtulzTrnsf;
    }

    public String getCodSprod() {
        return codSprod;
    }

    public void setCodSprod(String codSprod) {
        this.codSprod = codSprod;
    }

    public BigInteger getNumReftPgto() {
        return numReftPgto;
    }

    public void setNumReftPgto(BigInteger numReftPgto) {
        this.numReftPgto = numReftPgto;
    }

    public BigInteger getCodTrans() {
        return codTrans;
    }

    public void setCodTrans(BigInteger codTrans) {
        this.codTrans = codTrans;
    }

    public BigInteger getCodMotvo() {
        return codMotvo;
    }

    public void setCodMotvo(BigInteger codMotvo) {
        this.codMotvo = codMotvo;
    }

    public BigInteger getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(BigInteger referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getPaymentConcept() {
        return paymentConcept;
    }

    public void setPaymentConcept(String paymentConcept) {
        this.paymentConcept = paymentConcept;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public String getComements() {
        return comements;
    }

    public void setComements(String comements) {
        this.comements = comements;
    }

    public String getStampDateTime() {
        return stampDateTime;
    }

    public void setStampDateTime(String stampDateTime) {
        this.stampDateTime = stampDateTime;
    }
}
