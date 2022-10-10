package com.example.etutorbackend.service;

import com.example.etutorbackend.exception.LessonRangeNotFoundException;
import com.example.etutorbackend.exception.PlaceNotFoundException;
import com.example.etutorbackend.exception.SubjectNotFoundException;
import com.example.etutorbackend.model.entity.*;
import com.example.etutorbackend.model.payload.availibility.AvailabilityPayloadRequest;
import com.example.etutorbackend.model.payload.advertisement.AdvertisementPayloadRequest;
import com.example.etutorbackend.repository.AdvertisementRepository;
import com.example.etutorbackend.repository.LessonRangeRepository;
import com.example.etutorbackend.repository.PlaceRepository;
import com.example.etutorbackend.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.etutorbackend.model.entity.AdvertisementType.LOOKING_FOR_STUDENT;
import static com.example.etutorbackend.model.entity.AdvertisementType.LOOKING_FOR_TUTOR;

@Service
@RequiredArgsConstructor
public class AdvertisementService {
    private static final String PLACE_NOT_FOUND_MESSAGE = "Not found place with name: %s";
    private static final String LESSON_RANGE_NOT_FOUND_MESSAGE = "Not found lesson range with name: %s";
    private static final String SUCCESSFULLY_ADVERTISEMENT_CREATION = "Successfully created advertisement";
    private static final String SUBJECT_NOT_FOUND_MESSAGE = "Not found subject %s";
    private final AdvertisementRepository advertisementRepository;
    private final PlaceRepository placeRepository;
    private final LessonRangeRepository lessonRangeRepository;
    private final SubjectRepository subjectRepository;

    @Transactional
    public String createAdvertisement(AdvertisementPayloadRequest advertisementPayloadRequest) {
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


        List<Availability> availabilities = buildAvailabilitiesFromPayloadList(advertisementPayloadRequest.getAvailabilityPayloads());

        Advertisement advertisement = buildAdvertisementFromPayload(advertisementPayloadRequest,
                subject,
                places,
                lessonRanges,
                availabilities);

        subject.getAdvertisements().add(advertisement);
        places.forEach((place -> place.getAdvertisements().add(advertisement)));
        lessonRanges.forEach((lessonRange -> lessonRange.getAdvertisements().add(advertisement)));
        availabilities.forEach((availability -> availability.setAdvertisement(advertisement)));
        
        advertisementRepository.save(advertisement);
        
        return SUCCESSFULLY_ADVERTISEMENT_CREATION;
    }

    private Advertisement buildAdvertisementFromPayload(AdvertisementPayloadRequest advertisementPayloadRequest,
                                                        Subject subject,
                                                        List<Place> places,
                                                        List<LessonRange> lessonRanges,
                                                        List<Availability> availabilities) {
        System.out.println(advertisementPayloadRequest.getAdvertisementType().name());
        return Advertisement.builder()
                .price(advertisementPayloadRequest.getPrice())
                .minutesDuration(advertisementPayloadRequest.getMinutesDuration())
                .shortDesc(advertisementPayloadRequest.getShortDescription())
                .content(advertisementPayloadRequest.getContent())
                .subject(subject)
                .places(places)
                .lessonRanges(lessonRanges)
                .advertisementType(
                        advertisementPayloadRequest.getAdvertisementType().name().equals("LOOKING_FOR_TUTOR") ?
                                LOOKING_FOR_TUTOR : LOOKING_FOR_STUDENT
                )
                .availabilities(availabilities)
                .build();
    }

    private List<Availability> buildAvailabilitiesFromPayloadList(List<AvailabilityPayloadRequest> availabilityPayloadRequests) {
        return availabilityPayloadRequests
                .stream()
                .map((availabilityPayloadRequest -> Availability.builder()
                        .hourStart(availabilityPayloadRequest.getStartHour())
                        .hourEnd(availabilityPayloadRequest.getEndHour())
                        .day(availabilityPayloadRequest.getDayName())
                        .build()))
                .toList();
    }
}
