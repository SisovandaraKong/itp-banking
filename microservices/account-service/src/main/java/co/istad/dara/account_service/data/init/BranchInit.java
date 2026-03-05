package co.istad.dara.account_service.data.init;

import co.istad.dara.account_service.data.entity.BranchEntity;
import co.istad.dara.account_service.data.repository.BranchRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BranchInit {

    private final BranchRepository branchRepository;

    @PostConstruct
    public void branchInit(){
        if (branchRepository.count() == 0){

            BranchEntity phnomPenh = new BranchEntity();
            phnomPenh.setBranchId(UUID.randomUUID());
            phnomPenh.setName("PHNOM PENH");
            phnomPenh.setIsOpening(true);

            BranchEntity svayRieng = new BranchEntity();
            svayRieng.setBranchId(UUID.randomUUID());
            svayRieng.setName("SVAY RIENG");
            svayRieng.setIsOpening(true);

            branchRepository.saveAll(List.of(phnomPenh,svayRieng));
        }
    }
}
