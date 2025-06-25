package lucas.dev.backend.service;

import java.util.List;
import java.util.Map;

import lucas.dev.backend.model.Room;
import lucas.dev.backend.repository.RoomRepository;
import lucas.dev.backend.exception.RoomNotFoundException;
import lucas.dev.backend.exception.RoomNumberAlreadyExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class RoomService {

    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);
    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Transactional
    public Room save(Room room) {
        logger.info("Tentando salvar quarto com número: {}", room.getRoomNumber());
        if (roomRepository.existsByRoomNumber(room.getRoomNumber())) {
            logger.warn("Tentativa de criar quarto com número duplicado: {}", room.getRoomNumber());
            throw new RoomNumberAlreadyExistsException("Já existe um quarto com esse número: " + room.getRoomNumber());
        }
        Room savedRoom = roomRepository.save(room);
        logger.info("Quarto salvo com sucesso. ID: {}, Número: {}", savedRoom.getId(), savedRoom.getRoomNumber());
        return savedRoom;
    }

    @Transactional
    public void deleteById(Long id) {
        logger.info("Tentando deletar quarto com ID: {}", id);
        if(!roomRepository.existsById(id)){
            logger.warn("Tentativa de deletar quarto inexistente. ID: {}", id);
            throw new RoomNotFoundException("Room Not Found");
        }
        roomRepository.deleteById(id);
        logger.info("Quarto deletado com sucesso. ID: {}", id);
    }

    public Room findById(Long id) {
        logger.debug("Buscando quarto por ID: {}", id);
        Room room = roomRepository.findById(id)
            .orElseThrow(() -> {
                logger.warn("Quarto não encontrado. ID: {}", id);
                return new RoomNotFoundException("Room Not Found");
            });
        logger.debug("Quarto encontrado. ID: {}, Número: {}", room.getId(), room.getRoomNumber());
        return room;
    }

    public List<Room> findAll() {
        logger.debug("Buscando todos os quartos");
        List<Room> rooms = roomRepository.findAll();
        logger.debug("Encontrados {} quartos", rooms.size());
        return rooms;
    }

    @Transactional
    public Room update(Long id, Room room) {
        logger.info("Tentando atualizar quarto com ID: {}", id);
        Room existingRoom = roomRepository.findById(id)
            .orElseThrow(() -> {
                logger.warn("Tentativa de atualizar quarto inexistente. ID: {}", id);
                return new RoomNotFoundException("Room Not Found");
            });
        existingRoom.setRoomNumber(room.getRoomNumber());
        existingRoom.setRoomDescription(room.getRoomDescription());
        existingRoom.setValuePerDay(room.getValuePerDay());
        existingRoom.setVacant(room.isVacant());
        existingRoom.setRoomStatus(room.getRoomStatus());
        Room updatedRoom = save(existingRoom);
        logger.info("Quarto atualizado com sucesso. ID: {}, Número: {}", updatedRoom.getId(), updatedRoom.getRoomNumber());
        return updatedRoom;
    }

    @Transactional
    public Room partialUpdate(Long id, Map<String, Object> updates) {
        logger.info("Tentando atualização parcial do quarto com ID: {}. Campos: {}", id, updates.keySet());
        Room room = roomRepository.findById(id)
            .orElseThrow(() -> {
                logger.warn("Tentativa de atualização parcial em quarto inexistente. ID: {}", id);
                return new RoomNotFoundException("Room Not Found");
            });
        if (updates.containsKey("isVacant")) {
            room.setVacant((Boolean) updates.get("isVacant"));
            logger.info("Campo isVacant atualizado para: {}", room.isVacant());
        }
        roomRepository.save(room);
        logger.info("Atualização parcial concluída. ID: {}", id);
        return room;
    }
}
