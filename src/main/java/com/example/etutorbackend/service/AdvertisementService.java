package com.example.etutorbackend.service;

import com.example.etutorbackend.exception.*;
import com.example.etutorbackend.mapper.AdvertisementPayloadResponseMapper;
import com.example.etutorbackend.model.entity.*;
import com.example.etutorbackend.model.payload.advertisement.AdvertisementPayloadResponse;
import com.example.etutorbackend.model.payload.availibility.AvailabilityPayload;
import com.example.etutorbackend.model.payload.advertisement.AdvertisementPayloadRequest;
import com.example.etutorbackend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.etutorbackend.model.entity.AdvertisementType.LOOKING_FOR_STUDENT;
import static com.example.etutorbackend.model.entity.AdvertisementType.LOOKING_FOR_TUTOR;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdvertisementService {
    private static final String PLACE_NOT_FOUND_MESSAGE = "Not found place with name: %s";
    private static final String LESSON_RANGE_NOT_FOUND_MESSAGE = "Not found lesson range with name: %s";
    private static final String SUCCESSFULLY_ADVERTISEMENT_CREATION = "Successfully created advertisement";
    private static final String SUBJECT_NOT_FOUND_MESSAGE = "Not found subject %s";
    private static final String CITY_NOT_FOUND_MESSAGE = "Not found city %s";
    private static final String USER_NOT_FOUND_MESSAGE = "Not found user with id %d";
    private static final String ADVERTISEMENT_NOT_FOUND_MESSAGE = "Not found advertisement with id %d";
    private final AdvertisementRepository advertisementRepository;
    private final PlaceRepository placeRepository;
    private final CityRepository cityRepository;
    private final LessonRangeRepository lessonRangeRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    @Transactional
    public String createAdvertisement(AdvertisementPayloadRequest advertisementPayloadRequest) {
        User user = userRepository.findById(advertisementPayloadRequest.getAuthorId())
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, advertisementPayloadRequest.getAuthorId())));

        List<Place> places = advertisementPayloadRequest.getPlacesNames()
                .stream()
                .map((placeName) -> placeRepository.findByName(placeName)
                        .orElseThrow(() -> new PlaceNotFoundException(String.format(PLACE_NOT_FOUND_MESSAGE, placeName))))
                .toList();

        List<LessonRange> lessonRanges = advertisementPayloadRequest.getLessonRanges()
                .stream()
                .map((lessonRangeName) -> lessonRangeRepository.findByName(lessonRangeName)
                        .orElseThrow(() -> new LessonRangeNotFoundException(String.format(LESSON_RANGE_NOT_FOUND_MESSAGE, lessonRangeName))))
                .toList();

        Subject subject = subjectRepository.findByName(advertisementPayloadRequest.getSubjectName())
                .orElseThrow(() -> new SubjectNotFoundException(String.format(SUBJECT_NOT_FOUND_MESSAGE, advertisementPayloadRequest.getSubjectName())));

        City city = cityRepository.findByName(advertisementPayloadRequest.getCityName())
                .orElseThrow(() -> new CityNotFoundException(String.format(CITY_NOT_FOUND_MESSAGE, advertisementPayloadRequest.getCityName())));

        List<Availability> availabilities = buildAvailabilitiesFromPayloadList(advertisementPayloadRequest.getAvailabilityPayloads());

        Advertisement advertisement = buildAdvertisementFromPayload(advertisementPayloadRequest,
                subject,
                places,
                city,
                lessonRanges,
                availabilities,
                user);

        subject.getAdvertisements().add(advertisement);
        places.forEach((place -> place.getAdvertisements().add(advertisement)));
        city.getAdvertisements().add(advertisement);
        lessonRanges.forEach((lessonRange -> lessonRange.getAdvertisements().add(advertisement)));
        availabilities.forEach((availability -> availability.setAdvertisement(advertisement)));
        user.getAdvertisements().add(advertisement);

        advertisementRepository.save(advertisement);
        
        return SUCCESSFULLY_ADVERTISEMENT_CREATION;
    }

    private Advertisement buildAdvertisementFromPayload(AdvertisementPayloadRequest advertisementPayloadRequest,
                                                        Subject subject,
                                                        List<Place> places,
                                                        City city,
                                                        List<LessonRange> lessonRanges,
                                                        List<Availability> availabilities,
                                                        User user) {
        return Advertisement.builder()
                .price(advertisementPayloadRequest.getPrice())
                .minutesDuration(advertisementPayloadRequest.getMinutesDuration())
                .shortDesc(advertisementPayloadRequest.getShortDescription())
                .content(advertisementPayloadRequest.getContent())
                .subject(subject)
                .places(places)
                .city(city)
                .lessonRanges(lessonRanges)
                .advertisementType(
                        advertisementPayloadRequest.getAdvertisementType().name().equals("LOOKING_FOR_TUTOR") ?
                                LOOKING_FOR_TUTOR : LOOKING_FOR_STUDENT
                )
                .availabilities(availabilities)
                .user(user)
                .build();
    }

    private List<Availability> buildAvailabilitiesFromPayloadList(List<AvailabilityPayload> availabilityPayloadRequests) {
        return availabilityPayloadRequests
                .stream()
                .map((availabilityPayloadRequest -> Availability.builder()
                        .hourStart(availabilityPayloadRequest.getStartHour())
                        .hourEnd(availabilityPayloadRequest.getEndHour())
                        .day(availabilityPayloadRequest.getDayName())
                        .build()))
                .toList();
    }

    public Page<AdvertisementPayloadResponse> findAdvertisementsByKeyphrase(String keyPhrase, int pageNumber, int pageSize) {
        Page<Advertisement> advertisementsByKeyphrase
                = advertisementRepository.findAdvertisementsByShortDescOrSubjectName(keyPhrase, PageRequest.of(pageNumber, pageSize));

        return new PageImpl<>(
                advertisementsByKeyphrase
                        .stream()
                        .map(AdvertisementPayloadResponseMapper::mapToAdvertisementPayloadResponse)
                        .toList(),
                PageRequest.of(pageNumber, pageSize),
                advertisementsByKeyphrase.getTotalElements());
    }

    public Page<AdvertisementPayloadResponse> findAdvertisementsByAdvertisementType(String type, int pageNumber, int pageSize) {
        AdvertisementType advertisementType = null;
        switch (type) {
            case "student" -> advertisementType = LOOKING_FOR_TUTOR;
            case "tutor" -> advertisementType = LOOKING_FOR_STUDENT;
        };

        Page<Advertisement> advertisementsByAdvertisementType = advertisementRepository
                .findByAdvertisementType(advertisementType, PageRequest.of(pageNumber, pageSize));

        return new PageImpl<>(
                advertisementsByAdvertisementType
                        .stream()
                        .map(AdvertisementPayloadResponseMapper::mapToAdvertisementPayloadResponse)
                        .toList(),
                PageRequest.of(pageNumber, pageSize),
                advertisementsByAdvertisementType.getTotalElements());


    }

    public Page<AdvertisementPayloadResponse> findAdvertisementsByKeyphraseAndType(String keyPhrase, String type, int pageNumber, int pageSize) {
        AdvertisementType advertisementType = null;
        System.out.println(type);

        switch (type) {
            case "student" -> advertisementType = LOOKING_FOR_TUTOR;
            case "tutor" -> advertisementType = LOOKING_FOR_STUDENT;
        }

        Page<Advertisement> advertisements = null;

        if (type.equals("all")) {
            advertisements = advertisementRepository
                    .findByShortDescOrCategoryContaining(
                            keyPhrase,
                            PageRequest.of(pageNumber, pageSize));
        } else {
            advertisements = advertisementRepository
                    .findByShortDescOrCategoryAndAdvertisementTypeContaining(
                            keyPhrase,
                            advertisementType.name(),
                            PageRequest.of(pageNumber, pageSize));
        }

        return new PageImpl<>(
                advertisements
                        .stream()
                        .map(AdvertisementPayloadResponseMapper::mapToAdvertisementPayloadResponse)
                        .toList(),
                PageRequest.of(pageNumber, pageSize),
                advertisements.getTotalElements());

    }

    public Page<AdvertisementPayloadResponse> findAdvertisementsBySubjectName(int pageNumber, int pageSize, String subjectName) {
        Page<Advertisement> advertisementsBySubjectName
                = advertisementRepository.findAllBySubjectName(subjectName, PageRequest.of(pageNumber, pageSize));

        return new PageImpl<>(
                advertisementsBySubjectName
                        .stream()
                        .map(AdvertisementPayloadResponseMapper::mapToAdvertisementPayloadResponse)
                        .toList(),
                PageRequest.of(pageNumber, pageSize),
                advertisementsBySubjectName.getTotalElements());
    }

    public Page<AdvertisementPayloadResponse> findAdvertisementsByCityName(int pageNumber, int pageSize, String cityName) {
        Page<Advertisement> advertisementsBySubjectName
                = advertisementRepository.findAllByCityName(cityName, PageRequest.of(pageNumber, pageSize));

        return new PageImpl<>(
                advertisementsBySubjectName
                        .stream()
                        .map(AdvertisementPayloadResponseMapper::mapToAdvertisementPayloadResponse)
                        .toList(),
                PageRequest.of(pageNumber, pageSize),
                advertisementsBySubjectName.getTotalElements());
    }

    public AdvertisementPayloadResponse findAdvertisementById(Long advertisementId) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNotFoundException(String.format(ADVERTISEMENT_NOT_FOUND_MESSAGE, advertisementId)));

        return AdvertisementPayloadResponseMapper.mapToAdvertisementPayloadResponse(advertisement);
    }

    public List<AdvertisementPayloadResponse> findAdvertisementsByUserId(Long userId) {
        return advertisementRepository.findByUserId(userId)
                .stream()
                .map(AdvertisementPayloadResponseMapper::mapToAdvertisementPayloadResponse)
                .collect(Collectors.toList());
    }




}
