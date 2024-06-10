insert into shop (id, name, created_date, created_by, last_modified_by, last_modified_date)
values (1, 'shopA', NOW(), 'system', 'system', NOW()),
       (2, 'shopB', NOW(), 'system', 'system', NOW());
insert into product (id, name, shop_id, quantity, price, created_date, created_by, last_modified_by,
                     last_modified_date)
values (1, 'product1', 1, 100, 10.00, NOW(), 'system', 'system', NOW()),
       (2, 'product2', 1, 100, 10.00, NOW(), 'system', 'system', NOW()),
       (3, 'product3', 2, 3, 10.00, NOW(), 'system', 'system', NOW());