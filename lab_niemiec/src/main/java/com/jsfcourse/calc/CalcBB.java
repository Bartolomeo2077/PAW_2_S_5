package com.jsfcourse.calc;

import java.io.Serializable;
import java.util.ResourceBundle;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class CalcBB implements Serializable	{
	private String kwota;
	private String okres;
	private String procent;
	private Double result;

	@Inject
	@ManagedProperty("#{txtCalcErr}")
	private ResourceBundle txtCalcErr;

	// Resource injected
	@Inject
	@ManagedProperty("#{txtCalc}")
	private ResourceBundle txtCalc;

	@Inject
	FacesContext ctx;

	public String getKwota() {
		return kwota;
	}

	public void setKwota(String kwota) {
		this.kwota = kwota;
	}

	public String getOkres() {
		return okres;
	}

	public void setOkres(String okres) {
		this.okres = okres;
	}
	public String getProcent() {
		return procent;
	}

	public void setProcent(String procent) {
		this.procent = procent;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

	public boolean doTheMath() {
		try {
			double kwota = Double.parseDouble(this.kwota);
			double okres = Double.parseDouble(this.okres);
			double procent = Double.parseDouble(this.procent);

			result = (kwota * (procent/100)) / (okres/12);
			
			ctx.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, txtCalcErr.getString("calcComputationOkInfo"), null));
			ctx.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, txtCalc.getString("result") + ": " + result, null));

			return true;
				
		}
	catch (Exception e) {
        ctx.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas przetwarzania parametrów", null));
//        return false;
        return false;
    	}	
	}	
	
	// Go to "showresult" if ok
    public String calc() {
        if (doTheMath()) {
            return "showresult";
        }
        return null;
    }

    // Put result in messages on AJAX call
    public String calc_AJAX() {
        if (doTheMath()) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Wynik: " + result, null));
        }
        return null;
    }
	public String info() {
		return "info"; 
	}
}
