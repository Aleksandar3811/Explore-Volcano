package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountrySeedDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {
    private static final String FILE_PATH = "src/main/resources/files/json/countries.json";
    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public CountryServiceImpl(CountryRepository countryRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder sb = new StringBuilder();
        CountrySeedDto[] countrySeedDtos = this.gson.fromJson(new FileReader(FILE_PATH), CountrySeedDto[].class);
        for (CountrySeedDto countrySeedDto : countrySeedDtos) {
            Optional<Country> optionalCountry = this.countryRepository.findByName(countrySeedDto.getName());
            if (!this.validationUtil.isValid(countrySeedDto) || optionalCountry.isPresent()) {
                sb.append("Invalid country\n");
                continue;
            }

            Country country = this.modelMapper.map(countrySeedDto, Country.class);
            this.countryRepository.saveAndFlush(country);
            sb.append(String.format("Successfully imported country %s - %s\n", country.getName(), country.getCapital()));
        }
        return sb.toString();
    }
}
