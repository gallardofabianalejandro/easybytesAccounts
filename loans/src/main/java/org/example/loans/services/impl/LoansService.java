package org.example.loans.services.impl;


import com.company.exceptionhandling.starter.domain.BusinessException;
import org.example.loans.constants.LoansConstants;
import org.example.loans.dto.LoansDto;
import org.example.loans.entity.Loans;
import org.example.loans.entity.LoansMapper;
import org.example.loans.exceptions.LoansErrorCodes;
import org.example.loans.repositories.LoansRepository;
import org.example.loans.services.ILoansService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LoansService implements ILoansService {

    private final LoansRepository loansRepository;
    private final LoansMapper loansMapper;

    public LoansService(LoansRepository loansRepository, LoansMapper loansMapper) {
        this.loansRepository = loansRepository;
        this.loansMapper = loansMapper;
    }

    @Override
    public void createLoan(String mobileNumber) {

        loansRepository.findByMobileNumber(mobileNumber).ifPresent(loans -> {
            throw BusinessException.of(LoansErrorCodes.LOAN_ALREADY_EXISTS,
                    "mobileNumber", mobileNumber);
        });



        loansRepository.save(createNewLoan(mobileNumber));
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new loan details
     */
    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> BusinessException.of(LoansErrorCodes.LOAN_NOT_FOUND, "mobileNumber", mobileNumber)
        );
        return loansMapper.toDto(loans);
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByMobileNumber(loansDto.mobileNumber()).orElseThrow(
                () -> BusinessException.of(LoansErrorCodes.LOAN_NOT_FOUND, "mobileNumber", loansDto.mobileNumber())
        );
        loansRepository.save(loansMapper.partialUpdate(loansDto, loans));
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> BusinessException.of(LoansErrorCodes.LOAN_NOT_FOUND, "mobileNumber", mobileNumber)
        );
        loansRepository.delete(loans);
        return true;
    }
}
