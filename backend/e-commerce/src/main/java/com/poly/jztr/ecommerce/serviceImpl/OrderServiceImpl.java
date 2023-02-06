package com.poly.jztr.ecommerce.serviceImpl;

import com.google.gson.*;
import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.dto.OrderDto;
import com.poly.jztr.ecommerce.dto.OrderItemDto;
import com.poly.jztr.ecommerce.model.*;
import com.poly.jztr.ecommerce.repository.OrderItemRepository;
import com.poly.jztr.ecommerce.repository.OrderRepository;
import com.poly.jztr.ecommerce.service.OrderItemService;
import com.poly.jztr.ecommerce.service.OrderService;
import com.poly.jztr.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;
import com.poly.jztr.ecommerce.service.PromotionService;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository repository;

    @Autowired
    PromotionService promoService;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderItemService orderItemService;


    @Autowired
    PromotionService promotionService;

    @Autowired
    ProductService productService;

    @Override
    public List<Order> findByDate(Instant start, Instant end) {
        if (start == null) start = Instant.parse("2021-02-09");
        if (end == null) end = Instant.now();
        return repository.findByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(start, end);
    }

    @Override
    public List<Long> orderIds(List<Order> orders) {
        return orders.stream().map(Order::getId).collect(Collectors.toList());
    }

    @Override
    public Order toOrder(OrderDto dto) {
        Order order = new Order();
        order.setAddress(new Address(dto.getAddressId()));
        order.setDescription(dto.getDescription());
        order.setStatus(Constant.ORDER_STATUS_PENDING);
        order.setOrderItems(orderItemService.toOrderItem(dto.getItems()));
        return order;
    }

    @Override
    public <S extends Order> S save(S entity) {
        entity = repository.save(entity);
        S finalEntity = entity;
        List<OrderItem> orderItems = entity.getOrderItems().stream().
                map(orderItem -> {
                    orderItem.setOrder(finalEntity);
// minus quantity
                    return orderItem;
                }).collect(Collectors.toList());

        Double total = 0.0;
        for (OrderItem orderItem: orderItems) {
            total = orderItem.getUnitPrice() * orderItem.getQuantity();
            orderItemRepository.save(orderItem);
        }

        entity.setTotal(total);
        return repository.save(entity);

    }

    @Override
    @Transactional()
    public <S extends Order> S save(S entity, String code){
        List<OrderItem> orderItems = entity.getOrderItems();
        entity = repository.save(entity);
        S finalEntity = entity;
        AtomicReference<Double> total = new AtomicReference<>(0.0);
         orderItems.stream().
                map(orderItem -> {
                    orderItem.setOrder(finalEntity);
                    total.set(orderItem.getUnitPrice() * orderItem.getQuantity());
                    orderItemRepository.save(orderItem);
                    return orderItem;
                }).collect(Collectors.toList());
        if(code != null || !((code+"").equalsIgnoreCase("null"))){
            Optional<Promotion> optPro = promoService.findByCode(code);
            boolean check = checkPromo(optPro);
            if(check) {
                if(optPro.get().getAmount() >= total.get()) {
                    entity.setTotal(0.0);
                    entity.setPromotion(optPro.get());
                } else {
                    entity.setTotal(total.get() - optPro.get().getAmount());
                    entity.setPromotion(optPro.get());
                }
            } else {
                throw new TransactionSystemException("promotionInvalid");
            }
        }else{
            entity.setTotal(total.get());
        }
        return repository.save(entity);
    }

    private boolean checkPromo(Optional<Promotion> optPro) {
        if(optPro.isEmpty()) return false;

        Promotion promotion = optPro.get();
        if(promotion.getStatus()){
            if(promotion.getEndDate().isBefore(Instant.now())){
                return false;
            }
            if(promotion.getDeletedAt() != null) return false;
            else{
                promotion.setDeletedAt(Instant.now());
                promoService.save(promotion);
                return true;
            }
        }
        return true;
    }


    @Override
    public Page<Order> findByUser(Pageable page, User user){
        return repository.findByUser(user, page);
    }

    @Override
    public Page<Order> findByStatusIs(Integer status, Pageable pageable) {
        return repository.findByStatusIs(status, pageable);
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id);
    }


    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Double totalRevenueThisMonth(){
        String time = LocalDate.now().withDayOfMonth(1) + "";
        Optional<Double> optional = repository.totalRevenueInThisMonth(time);
        if(optional.isPresent()) return optional.get();
        return 0D;
    }

    @Override
    public List<Object[]> totalRevenuePerMonth(){
        return repository.totalRevenuePerMonth();
    }

    @Override
    public Page<Order> findByUsername(String firstName, String lastName, Pageable pageable) {
        return  repository.findByUserFirstNameContainsOrUserLastNameContains(firstName, lastName, pageable);
    }


    @Override
    public List<Order> findByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(Instant start, Instant end) {
        return repository.findByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(start, end);
    }

    @Override
    public Page<Order> findByStatusAndCreatedAtAfterAndCreatedAtBeforeAndTotalGreaterThanAndTotalLessThan
            (Integer status, Instant start, Instant end, Double min, Double max, String name,Pageable pageable) {
        return repository.findByStatusAndCreatedAtAfterAndCreatedAtBeforeAndTotalGreaterThanEqualAndTotalLessThanEqualAndUserFirstNameContains(
                status, start, end, min, max, name, pageable);
    }

    @Override
    public Page<Order> findByCreatedAtAfterAndCreatedAtBeforeAndTotalGreaterThanAndTotalLessThan
            (Instant start, Instant end, Double min, Double max, String name , Pageable pageable) {
        return repository.findByCreatedAtAfterAndCreatedAtBeforeAndTotalGreaterThanEqualAndTotalLessThanEqualAndUserFirstNameContains
                (start, end, min, max, name,pageable);
    }

    @Override
    public Page<Order> findByStatusAndCreatedAtAfterAndCreatedAtBeforeAndTotalGreaterThanEqualAndTotalLessThanEqualAndUserFirstNameContainsAndUserEmailContainsAndUserPhoneContainsAndIdContains(Integer status, Instant start, Instant end, Double min, Double max, String name, String email, String phone, Pageable pageable) {
        return repository.findByStatusAndCreatedAtAfterAndCreatedAtBeforeAndTotalGreaterThanEqualAndTotalLessThanEqualAndUserFirstNameContainsAndUserEmailContainsAndUserPhoneContains(status, start, end, min, max, name, email, phone, pageable);
    }
    @Override
    public Page<Order> findByCreatedAtAfterAndCreatedAtBeforeAndTotalGreaterThanEqualAndTotalLessThanEqualAndUserFirstNameContainsAndUserEmailContainsAndUserPhoneContainsAndIdContains(Instant start, Instant end, Double min, Double max, String name, String email, String phone, Pageable pageable) {
        return repository.findByCreatedAtAfterAndCreatedAtBeforeAndTotalGreaterThanEqualAndTotalLessThanEqualAndUserFirstNameContainsAndUserEmailContainsAndUserPhoneContains(start, end, min, max, name, email, phone, pageable);
    }

    @Override
    public Optional<Order> findByPromotion(String code) {
        return repository.findByPromotionCodeContains(code);
    }

    @Override
    public Order updateStatus(Order order) {
        return repository.save(order);
    }

    @Override
    public Page<Order> findByPhone(String phone, Pageable pageable) {
        return repository.findByAddressPhone(phone, pageable);
    }

    @Override
    @Async
    public void checkPayment(Long id) throws InterruptedException {
        Order order = repository.findById(id).get();
        for (int i = 0; i < 18; i ++){
            if(checkMomo(order.getPaymentCode(), order.getTotal())){
                order.setStatus(Constant.ORDER_STATUS_PENDING);
                save(order);
                return;
            }
            if (i == 17){
                order.setDeletedAt(Instant.now());
                save(order);
                return;
            }
            Thread.sleep(10000);
        }
    }

    @Override
    public void updateQuantity(List<OrderItemDto> dtos) {
        dtos.stream().forEach((dto) -> {
           Product p = productService.findById(dto.getProductId()).get();
           p.setQuantity(p.getQuantity() - dto.getQuantity());
           productService.save(p);
        });
    }

    @Override
    public Page<Order> findByUserAndStatus(User user, int status, Pageable pageable) {
        return repository.findByUserAndStatus(user, status, pageable);
    }

    private boolean checkMomo(String paymentCode, double total){
        try{
//            String accessToken = "lKUQyUPaG5hdMcJzQvSrFfDhckl8LTVdACbVCG3e6AG3zmGI1B";
//            WebClient client = WebClient.create("https://momosv3.apimienphi.com/api/checkTranContent");
//            System.out.println("Send Momo");
//            String json = client.post().body(BodyInserters.fromObject(new Body(accessToken,paymentCode))).exchange()
//                    .block().bodyToMono(String.class).block();

            RestTemplate restTemplate = new RestTemplate();
            Gson gson = new GsonBuilder().create();
            JsonElement jsonElement = gson.toJsonTree(new Body(paymentCode));
            JsonObject jsonObject = (JsonObject) jsonElement;


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

            String json = restTemplate.exchange("https://momosv3.apimienphi.com/api/checkTranContent", HttpMethod.POST,
                    entity,String.class).getBody();
            System.out.println(json);
            jsonObject = JsonParser.parseString(json).getAsJsonObject();
            Long totalStringLong = Long.valueOf(jsonObject.get("data").getAsJsonObject().get("amount").getAsString());
            if (((totalStringLong +1) > total) && ((total + 1) > totalStringLong)){
                return true;
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private class Body{
        private String access_token;
        private String phone;
        private String content;

        public Body(String content){
            this.access_token = "lKUQyUPaG5hdMcJzQvSrFfDhckl8LTVdACbVCG3e6AG3zmGI1B";
            this.phone = "0973588761";
            this.content = content;
        }
    }
}
