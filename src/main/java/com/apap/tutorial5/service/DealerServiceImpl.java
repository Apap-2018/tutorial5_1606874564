package com.apap.tutorial5.service;
import java.util.List;
import java.util.Optional;
import com.apap.tutorial5.model.DealerModel;
import com.apap.tutorial5.repository.DealerDb;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tutorial5.repository.DealerDb;

@Service
@Transactional
public class DealerServiceImpl implements DealerService{
	@Autowired
	private DealerDb dealerDb;
	
	@Override
	public Optional<DealerModel> getDealerDetailById(Long id){
		return dealerDb.findById(id);
	}
	
	@Override
	public void addDealer(DealerModel dealer) {
		dealerDb.save(dealer);
	}
	
	@Override
	public void deleteDealer(DealerModel dealer) {
		dealerDb.delete(dealer);
	}
	
	@Override
	public void updateDealer(Long id, Optional<DealerModel> newDealer) {
		DealerModel updated = dealerDb.getOne(id);
		updated.setAlamat(newDealer.get().getAlamat());
		updated.setNoTelp(newDealer.get().getNoTelp());
		dealerDb.save(updated);
	}
	
	@Override
	public List<DealerModel> getAllDetailDealer(){
		return dealerDb.findAll();
	}

}
