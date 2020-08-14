package com.example.encryptionweb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

    @Controller
    public class EncryptionController {

        @GetMapping("/encryption")
        public String encryptionForm(Model model) {
            model.addAttribute("encryption", new Encryption() );
            return "encryption";
        }

        @PostMapping("/encryption")
        public String encryptionSubmit(@ModelAttribute Encryption encryptionResult, Model model) throws Exception {
            encryptionResult = EncryptionUtil.encryptAES(encryptionResult.getText());
            model.addAttribute("encryption",encryptionResult );
            return "encryptionResult";
        }

        @GetMapping("/decryption")
        public String decryptionForm(Model model) {
            model.addAttribute("decryption", new Encryption() );
            return "decryption";
        }

        @PostMapping("/decryption")
        public String decryptionSubmit(@ModelAttribute Encryption encryption, Model model) throws Exception {
            String decrypted = EncryptionUtil.decryptAES(encryption.getText(), encryption.getKey());
            Encryption decryptedText = new Encryption(decrypted, "");
            model.addAttribute("decryption",decryptedText );
            return "decryptionResult";
        }

    }
