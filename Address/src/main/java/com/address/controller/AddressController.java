package com.address.controller;

import com.address.model.dto.AddressDto;
import com.address.model.dto.AddressRequest;
import com.address.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    AddressService addressService;

    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }

    @PostMapping("/save")
    public ResponseEntity<List<AddressDto>> saveAddress(@RequestBody AddressRequest addressRequest){
        List<AddressDto> response = addressService.saveAddress(addressRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<List<AddressDto>> updateAddress(@RequestBody AddressRequest addressRequest){
        List<AddressDto> response = addressService.updateAddress(addressRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id){
        addressService.deleteAddressById(id);
        return new ResponseEntity<>("Deleted address with id: " + id, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AddressDto>> getAllAddress(){
        List<AddressDto> response = addressService.getAllAddress();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<AddressDto>> getAddressByEmpId(@PathVariable Long id){
        List<AddressDto> response = addressService.getAddressByEmpId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
