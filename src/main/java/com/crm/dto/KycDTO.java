package com.crm.dto;

public class KycDTO {
        private String reference_id;
        private String document_type;
        private String id_number;
        private String consent;
        private String consent_purpose;
        private String gstin;
        private String pan;

        public String getPan() {
			return pan;
		}

		public void setPan(String pan) {
			this.pan = pan;
		}

		public KycDTO() {
        }

        public String getReference_id() {
                return reference_id;
        }

        public void setReference_id(String reference_id) {
                this.reference_id = reference_id;
        }

        public String getDocument_type() {
                return document_type;
        }

        public void setDocument_type(String document_type) {
                this.document_type = document_type;
        }

        public String getId_number() {
                return id_number;
        }

        public void setId_number(String id_number) {
                this.id_number = id_number;
        }

        public String getConsent() {
                return consent;
        }

        public void setConsent(String consent) {
                this.consent = consent;
        }

        public String getConsent_purpose() {
                return consent_purpose;
        }

        public void setConsent_purpose(String consent_purpose) {
                this.consent_purpose = consent_purpose;
        }

		

		public String getGstin() {
			return gstin;
		}

		public void setGstin(String gstin) {
			this.gstin = gstin;
		}

		@Override
		public String toString() {
			return "KycDTO [reference_id=" + reference_id + ", document_type=" + document_type + ", id_number="
					+ id_number + ", consent=" + consent + ", consent_purpose=" + consent_purpose + ", gstin=" + gstin
					+ ", pan=" + pan + "]";
		}

       
}
