package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.CryptoWaarde;
import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.support.api.CryptoAPI;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class CryptoWaardeService {
    private RootRepository rootRepository;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(CryptoWaardeService.class);

    private static final String DEFAULT_ID = "defaultId";

    /*
    roept 1x per dag de methode haalCryptoWaardes op
     */
    public void main(String[] args) {
        Timer timer = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                haalCryptoWaardes();
            }
        };
        timer.schedule(tt, new Date(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));

    }
    public CryptoWaardeService(RootRepository rootRepository){
        super();
        this.rootRepository = rootRepository;
    }

    public void slaCryptoWaardeOp(CryptoWaarde cryptoWaarde){
        rootRepository.slaCryptoWaardeOp(cryptoWaarde);
    }

    public CryptoWaarde vindCryptoWaarde(Cryptomunt cryptomunt){
        return rootRepository.haalMeestRecenteCryptoWaarde(cryptomunt);
    }

    public void haalCryptoWaardes(){
        for (int i = 0; i < cryptoLijst().size(); i++) {
            double cryptoDagwaarde = CryptoAPI.cryptoDagwaarde(cryptoLijst().get(i));
            LocalDate datum = LocalDate.now();
            CryptoWaarde cryptoWaarde = new CryptoWaarde(DEFAULT_ID, cryptoLijst().get(i), cryptoDagwaarde,datum);
            System.out.println(cryptoWaarde.getCryptomunt().getName() + cryptoWaarde.getWaarde());
            slaCryptoWaardeOp(cryptoWaarde);
        }
    }

    //TODO juiste plek voor aanmaken arraylist?
    public static ArrayList<Cryptomunt> cryptoLijst(){
        Cryptomunt bitcoin = new Cryptomunt(1, "bitcoin", "BTC");
        Cryptomunt ethereum = new Cryptomunt(1027, "ethereum", "ETH");
        Cryptomunt solana = new Cryptomunt(5426, "solana", "SOL");
        Cryptomunt binance = new Cryptomunt(1839, "binance-coin", "BNB");
        Cryptomunt cardano = new Cryptomunt(2010, "cardano", "ADA");
        Cryptomunt xrp = new Cryptomunt(52, "xrp", "XRP");
        Cryptomunt avalanche = new Cryptomunt(5805, "avalanche", "AVAX");
        Cryptomunt polkadot = new Cryptomunt(6636, "polkadot", "DOT");
        Cryptomunt terra = new Cryptomunt(4172, "terra", "LUNA");
        Cryptomunt dogecoin = new Cryptomunt(74, "dogecoin", "DOGE");
        Cryptomunt polygon = new Cryptomunt(3890, "polygon", "MATIC");
        Cryptomunt litecoin = new Cryptomunt(2, "litecoin", "LTC");
        Cryptomunt terrausd = new Cryptomunt(7129, "terrausd", "UST");
        Cryptomunt algorand = new Cryptomunt(4030, "algorand", "ALGO");
        Cryptomunt tron = new Cryptomunt(1958, "tron", "TRX");
        Cryptomunt bitcoin_cash = new Cryptomunt(1831, "bitcoin-cash", "BCH");
        Cryptomunt stellar = new Cryptomunt(512, "stellar", "XLM");
        Cryptomunt elrond = new Cryptomunt(6892, "elrond", "EGLD");
        Cryptomunt vechain = new Cryptomunt(3077, "vechain", "VET");
        Cryptomunt filecoin = new Cryptomunt(2280, "filecoin", "FIL");


        List<Cryptomunt> list = Arrays.asList(bitcoin, ethereum, solana, binance, cardano, xrp, avalanche, polkadot, terra, dogecoin,
                polygon, litecoin, terrausd, algorand, tron, bitcoin_cash, stellar, elrond, vechain, filecoin);

        return new ArrayList<>(list);

    }
}


